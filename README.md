# Project Overview

This is a mock project for a smart plant-monitoring system you can implement at home. Using esp-32 as devices, users are able to register/login to an account to access a jwt authenticated dashboard + device managing/registering page.
Currently the app is hosted locally so there is a need to enter local server ip address when registering a new monitoring device. The app is initialized with a few mock entities to play with,
but you can add your own. Why did I implement user security if I plan on keeping everything local? for fun...

**Seeded credentials**:
* InitialUsername: InitialUsername
* InitialPassword: InitialPassword
* deviceSecret = "PLANT-99-XZ"; 
# How to use
clone the git repo
run `mvn clean package -DskipTests` to build the artifact file
run `docker-compose up --build` to start the program 



## Configuring/building device
Build circuit according to schematic defined in firmware\SensorModuleSchematic.pdf
<br><br> Required components:
* tactile switch x1
* dht11 sensor
* esp32 wroom (devkit)
Upload code in firmware\esp32device.ino to the microcontroller. <br> Required dependencies:
*  Adafruit Unified Sensor library
*  DHT library (Adafruit)
*  WifiManager by Tzapu
<br>
## Setting up: 
<br><br>
Before uploading, replace serverIP value with your local machine's IP address (since this is hosted locally for now), you can check this by typing ipconfig in windows terminal, or ifconfig for unix
Once uploaded the esp32 will start up its own wifi portal. Search for this in networks and connect to it to input wifi credentials. The device will store this in it's NVS so it will only ask for credentials again if you change location or error connecting to wifi, otherwise wifi connection should be automatic on reboot.
<br><br>
If connection is successful, the serial monitor will display your device's mac address and close the wifi portal. You can press the button connected tp d22 to send a pending claim request to the server backend. The device will continue short polling the backend every ~3 seconds until frontend verification happens. (you can also check the status of the device from the LED - short blips indicate pending, long blips (1-2 seconds) indicate successful data transmission. )
<br><br>
You can log into localhost:8080 --> register device, enter the device mac address and deviceSecret. On successful verification, the device should start sending environment telemetry. You can see this on the dashboard interface, and refresh if you want the latest data. This data and device will only be visible on the logged-in account on which the device is registered. For test purposes the interval is set to 5 seconds. 



# + information
Spring boot Java 17 Maven C++ 

I used captive portal (via WiFiManager by Tzapu) to allow end-users to configure Wi-Fi credentials dynamically. 
Spring security is implemented to issue jwt tokens for user authentication, so all devices are only visible to the user it's registered to. Users which are not logged in are automatically redirected to the login page. 

# Demo: 
https://drive.google.com/file/d/12KQ0Ynb4koXozoYHKwrTiuFtvLDfJ9y8/view?usp=sharing

# maybe future features
- There is backend logic for removing devices, but no interface for users to relocate their devices yet, since all devices have a many to one mapping to users and are only visible to one person. Possibly in the future make it so that a device is visible to multiple users based on different levels of authority.
- <br>
- Similarily there is logic for displaying all the devices a user may own, although the dashboard is still being worked on.
- Might implement a future feature that lets a user delete their account. 
- Currently device uses HTTP protocol to communicate with server, but may migrate to MQTT for better battery and possibly event driven functions in the future
