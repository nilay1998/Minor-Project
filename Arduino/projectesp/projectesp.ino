#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>

const char* ssid = "slr";
const char* password = "abcd12345";
 
void setup () {
 
    Serial.begin(115200);
    WiFi.begin(ssid, password);
     
    while (WiFi.status() != WL_CONNECTED) 
    {
        delay(1000);
        Serial.print("Connecting..");
    }
 
}
 
void loop()
{
    int id=-1;
    String val;
    while(Serial.available())
    {
        val=Serial.readString();
        id=0;
    }
    if (WiFi.status() == WL_CONNECTED) 
    {
        Serial.print("CONNECTED");

        if(id!=-1)
        {
            StaticJsonBuffer<300> JSONbuffer;  
            JsonObject& JSONencoder = JSONbuffer.createObject(); 
            JSONencoder["id"] = val;
            char JSONmessageBuffer[300];
            JSONencoder.prettyPrintTo(JSONmessageBuffer, sizeof(JSONmessageBuffer));
            Serial.println(JSONmessageBuffer);
        
            HTTPClient http;   
 
            http.begin("http://192.168.69.128:3000/api/register");     
            http.addHeader("Content-Type", "application/json"); 
           
         
            int httpCode = http.POST(JSONmessageBuffer);   
            String payload = http.getString();                
         
            Serial.println(httpCode); 
            Serial.println(payload);
        }
    }
delay(2000);   
}
