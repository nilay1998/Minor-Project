#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>

const char* ssid = "pankaj";
const char* password = "pankaj897";
 
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
    String type;
    while(Serial.available())
    {
        val=Serial.readString();
        int index=val.indexOf(":");
        type=val.substring(index+1);
        val=val.substring(0,index);
        id=0;
    }
    if (WiFi.status() == WL_CONNECTED) 
    {
        Serial.print("CONNECTED");

        if(id!=-1)
        {
            if(type=="enroll")
            {
                StaticJsonBuffer<300> JSONbuffer;  
                JsonObject& JSONencoder = JSONbuffer.createObject(); 
                JSONencoder["id"] = val;
                char JSONmessageBuffer[300];
                JSONencoder.prettyPrintTo(JSONmessageBuffer, sizeof(JSONmessageBuffer));
                Serial.println(JSONmessageBuffer);
            
                HTTPClient http;   
     
                http.begin("http://192.168.43.9:3000/api/id");     
                http.addHeader("Content-Type", "application/json"); 
               
             
                int httpCode = http.POST(JSONmessageBuffer);   
                String payload = http.getString();                
             
                Serial.println(httpCode); 
                Serial.println(payload);
            }
            if(type=="add")
            {
                StaticJsonBuffer<300> JSONbuffer;  
                JsonObject& JSONencoder = JSONbuffer.createObject(); 
                JSONencoder["id"] = val;
                char JSONmessageBuffer[300];
                JSONencoder.prettyPrintTo(JSONmessageBuffer, sizeof(JSONmessageBuffer));
                Serial.println(JSONmessageBuffer);
            
                HTTPClient http;   
     
                http.begin("http://192.168.43.9:3000/api/attendance");     
                http.addHeader("Content-Type", "application/json"); 
               
             
                int httpCode = http.PUT(JSONmessageBuffer);   
                String payload = http.getString();                
             
                Serial.println(httpCode); 
                Serial.println(payload); 
            }
        }
    }
delay(2000);   
}
