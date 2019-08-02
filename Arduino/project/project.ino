
#include "FPS_GT511C3.h"
#include "SoftwareSerial.h"
#include <LiquidCrystal.h>

LiquidCrystal lcd(2,3,4,5,6,7);
SoftwareSerial mySerial(10, 11);
FPS_GT511C3 fps(12, 13); // (Arduino SS_RX = pin 10, Arduino SS_TX = pin 11)
 

void setup()
{
  Serial.begin(9600); //set up Arduino's hardware serial UART 
  delay(100);
  mySerial.begin(115200);
  fps.Open();         //send serial command to initialize fps
  fps.SetLED(true);   //turn on LED so fps can see fingerprint
  lcd.begin(16, 2);
  lcd.print("Hey");

  delay(2000);
}

void Enroll()
{
  delay(1000);
  // Enroll test

  // find open enroll id
  int enrollid = 0;
  bool usedid = true;
  while (usedid == true)
  {
    usedid = fps.CheckEnrolled(enrollid);
    if (usedid==true) enrollid++;
  }
  fps.EnrollStart(enrollid);

  // enroll
  lcd.clear();
  lcd.print("Press finger to ");
  lcd.setCursor(0,1);
  lcd.print("Enroll #");
  lcd.println(enrollid);
  while(fps.IsPressFinger() == false) delay(100);
  bool bret = fps.CaptureFinger(true);
  int iret = 0;
  if (bret != false)
  {
    delay(500);
    lcd.clear();
    lcd.println("Remove finger");
    fps.Enroll1(); 
    while(fps.IsPressFinger() == true) delay(100);
    delay(500);
    lcd.clear();
    lcd.println("Press same finge");
    lcd.setCursor(0,1);
    lcd.print("r again");
    while(fps.IsPressFinger() == false) delay(100);
    bret = fps.CaptureFinger(true);
    if (bret != false)
    {
      delay(500);
      lcd.clear();
      lcd.println("Remove finger");
      fps.Enroll2();
      while(fps.IsPressFinger() == true) delay(100);
      delay(500);
      lcd.clear();
      lcd.println("Press same finge");
      lcd.setCursor(0,1);
      lcd.print("r yet again");
      while(fps.IsPressFinger() == false) delay(100);
      bret = fps.CaptureFinger(true);
      if (bret != false)
      {
        delay(500);
        lcd.clear();
        lcd.println("Remove finger");
        iret = fps.Enroll3();
        if (iret == 0)
        {
          delay(500);
          lcd.clear();
          lcd.println("Enrolling Succes");
          lcd.setCursor(0,1);
          lcd.print("sful");
          String val=String(enrollid);
          mySerial.print(val);
          delay(1000);
        }
        else
        {
          delay(500);
          lcd.clear();
          lcd.print("Enrolling Failed");
          lcd.setCursor(0,1);
          lcd.print(" Error code:");
          lcd.println(iret);
          delay(1000);
        }
      }
      else {delay(500);lcd.clear();lcd.println("Failed to captur");lcd.setCursor(0,1);
          lcd.print("e third finger");}
    }
    
    else {delay(500);lcd.clear();lcd.println("Failed to captur");lcd.setCursor(0,1);
          lcd.print("e second finger");}
  }
  
  else {delay(500);lcd.clear(); lcd.println("Failed to captur");lcd.setCursor(0,1);
          lcd.print("e first finger");}
}

void Identify()
{
  while(fps.IsPressFinger() == false)
  {
    lcd.clear();
    lcd.println("Please press fin");
    lcd.setCursor(0,1);
    lcd.print("ger"); 
    delay(100);
  }
  if (fps.IsPressFinger())
  {
    fps.CaptureFinger(false);
    int id = fps.Identify1_N();
    
    if (id <200) //<- change id value depending model you are using
    {//if the fingerprint matches, provide the matching template ID
      lcd.clear();
      lcd.print("Verified ID:");
      lcd.println(id);
      if(id==0)
      return Enroll();
      delay(1500);
    }
    else
    {//if unable to recognize
      lcd.clear();
      lcd.println("Finger not found");
      delay(500);
    }
  }
  else
  {
    lcd.clear();
    lcd.println("Unable to identify");
    delay(500);
  }
  delay(100);
}


void loop()
{
  lcd.clear();
//  Enroll();
  Identify();
  delay(1000);
}
