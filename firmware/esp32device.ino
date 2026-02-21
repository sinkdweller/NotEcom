#include <WiFiManager.h>
#include <HTTPClient.h>
#include "DHT.h"

#define BUTTON 22
#define LED 23
#define DHTPIN 5
#define DHTTYPE DHT11 

String serverIP = "192.168.2.100";
String serverPort = "8080";

String deviceSecret = "PLANT-99-XZ"; 
String mac;

//initialize the DHT sensor
DHT dht(DHTPIN, DHTTYPE);
void setup() {
    Serial.begin(115200);
    delay(1000); // Give Serial time to initialize

    pinMode(BUTTON, INPUT_PULLUP);
    pinMode(LED, OUTPUT);
    
    WiFiManager wifiManager;
    // This will block until connected or timed out
    bool res = wifiManager.autoConnect("ESP32_Setup_Portal"); 

    if(!res) {
        Serial.println("Failed to connect. Restarting...");
        delay(3000);
        ESP.restart();
    } else {
        mac = WiFi.macAddress();
        Serial.println("Connected to Wi-Fi!");
        Serial.println("Your Mac address is: " + mac);
    }
    dht.begin();
}
//claim Logic
bool sentClaimRequest = false;
//Button logic
unsigned int debounceTime = 50;
bool lastStableState = HIGH;
unsigned long lastDebounceTime = 0;
bool lastFlickerState = HIGH;
bool buttonState;

//Upload Logic
unsigned long lastTimeSent = 0;
unsigned long timerDelay = 3000;  //three seconds

unsigned long futureLedOffTime;
unsigned long successLedDuration = 1000;
unsigned long failureLedDuration = 50;

void loop() {
  if (WiFi.status() == WL_CONNECTED) {
    
    // Ensure mac is populated
    if (mac == "") {
      mac = WiFi.macAddress();
    }
    
    //CLAIM logic
    if(!sentClaimRequest){
      buttonState = digitalRead(BUTTON);

      //handle flicker
      if(buttonState != lastFlickerState){
        lastFlickerState = buttonState;
        lastDebounceTime = millis();
      }

      if(millis()-lastDebounceTime > debounceTime){
        if(lastStableState==HIGH && buttonState==LOW){
          //Send claim request if button is pressed
          Serial.println("Sending claim...");
          int httpResponse = sendPostRequest("/api/device/pendClaim", "{\"macAddress\":\"" + mac + "\", \"generatedCode\":\"" + deviceSecret + "\"}");
          if(httpResponse ==200){
            sentClaimRequest = true;
          }
          
          lastTimeSent = millis(); 
        }
        lastStableState = buttonState;
          
      }
    }
    
    //UPLOAD logic + short polling: timerDelay set to every 3 seconds.
    if(sentClaimRequest){
      if ((millis() - lastTimeSent) > timerDelay) {
        lastTimeSent = millis();
        float temperature = dht.readTemperature(); //celcius reading, pass true for farenheit
        float humidity = dht.readHumidity();
        if(isnan(humidity) || isnan(temperature)){
          Serial.println("failed to get temperature reading");
          return;
         }
        String jsonPayload = "{\"macAddress\":\"" + mac + "\",\"temperature\":" + temperature + ",\"soilMoisture\":"+ humidity +"}";
        int httpResponse = sendPostRequest("/api/device/upload", jsonPayload);
        
        //SHORT BLIPS = PENDING CLAIM, LONG BLIP = SUCCESSFUL DATA UPLOAD
        unsigned long visualSuccessFeedback = (httpResponse==200)?successLedDuration:failureLedDuration;
        futureLedOffTime = millis()+ visualSuccessFeedback;
        digitalWrite(LED, HIGH);
      }
      //Turn OFF light if it's on and overdue
      if(digitalRead(LED)==HIGH && millis()>futureLedOffTime){
        digitalWrite(LED, LOW);
      }
    }
  }
  yield(); //prevent watchdog
}

int sendPostRequest(String endpoint, String payload) {
    if (WiFi.status() != WL_CONNECTED) return -1;

    HTTPClient http;
    String url = "http://" + serverIP + ":" + serverPort + endpoint;    
    Serial.print("Attempting: "); Serial.println(url);

    http.begin(url);
    http.setConnectTimeout(1500); 
    http.setTimeout(1500);
    http.addHeader("Content-Type", "application/json");
    
    int httpResponseCode = http.POST(payload);
    
    if (httpResponseCode > 0) {
        Serial.print("Server Response: "); Serial.println(httpResponseCode);
    } else {
        Serial.print("HTTP Error connecting to server. ");
    }
    
    http.end();
    return httpResponseCode;

}