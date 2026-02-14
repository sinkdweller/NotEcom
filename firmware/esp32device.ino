#include <WiFiManager.h>
#include <HTTPClient.h>

// Variables for timing
unsigned long last_time = 0;
unsigned long timerDelay = 3000;  //three seconds

String deviceSecret = "PLANT-99-XZ"; 
String mac;

void setup() {
    Serial.begin(115200);
    delay(1000); // Give Serial time to initialize
        
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
        Serial.println("Target Server: http://192.168.50.10:8080");
    }
}

bool hasClaimed = false;

void loop() {
  if (WiFi.status() == WL_CONNECTED) {
    
    // Ensure mac is populated
    if (mac == "") {
      mac = WiFi.macAddress();
    }

    // Send Claim once
    if (!hasClaimed && mac != "") {
      Serial.println("Sending claim...");
      sendPostRequest("/api/device/pendClaim", "{\"macAddress\":\"" + mac + "\", \"generatedCode\":\"" + deviceSecret + "\"}");
      hasClaimed = true;
      last_time = millis(); 
    }

    if ((millis() - last_time) > timerDelay) {
      last_time = millis();
      String jsonPayload = "{\"macAddress\":\"" + mac + "\",\"temperature\":25.5,\"soilMoisture\":42}";
      sendPostRequest("/api/device/upload", jsonPayload);
    }
  }
  yield(); 
}

void sendPostRequest(String endpoint, String payload) {
    if (WiFi.status() != WL_CONNECTED) return;

    HTTPClient http;
    String url = "http://192.168.50.10:8080" + endpoint;
    
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
    yield(); 
}