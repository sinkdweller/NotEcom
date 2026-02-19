# Project Overview # I'm not done yet wait 

This is a mock project for a smart plant-monitoring system you can implement at home. Using esp-32 as devices, users are able to register/login to an account to access a jwt authenticated dashboard + device managing/registering page.
Currently the app is hosted locally (docker image is being worked on) so there is a need to enter local server ip address when registering a new monitoring device. The app is initialized with a few mock entities to play with,
but you can add your own. Why did I implement user security if I plan on keeping everything local? for fun

**Seeded credentials**:
* InitialUsername: InitialUsername
* InitialPassword: InitialPassword
* deviceSecret = "PLANT-99-XZ"; 
# How to use
I will upload building locally later instructions coming
/login.html --> this is the login page
/register.htnk --> this is the register page

## Setting up device
Build according to schematic defined in firmware\SensorModuleSchematic.pdf
Required components:
* tactile switch x1
* dht11 sensor
* esp32 wroom (devkit)
Upload code in firmware\esp32device.ino to the microcontroller. Required dependencies:
*  Adafruit Unified Sensor library
*  DHT library (Adafruit)
*  WifiManager by Tzapu
Replace serverIP value with your local machine's IP address (since this is hosted locally for now), you can check this by typing ipconfig in windows terminal, or ifconfig for unix
Once uploaded the esp32 will start up its own wifi portal. Search for this in networks and connect to it to input wifi credentials. The device will store this in it's NVS so it will only ask for credentials again if you change location, otherwise wifi connection should be automatic on reboot.

If connection is successful, the serial monitor will display your device's mac address and close the wifi portal. You can press the button connected tp d22 to send a pending claim request to the server backend. The device will continue short polling the backend every ~3 seconds until frontend verification happens.

You can log into localhost:8080 --> register device, enter the device mac address and deviceSecret. On successful verification, the device should start sending environment telemetry. You can see this on the dashboard interface, and refresh if you want the latest data. This data and device will only be visible on the logged-in account on which the device is registered. 


# API documentation
* POST auth/signup
> signup endpoint (permit all)
* POST auth/login
> login (permit all)
* GET api/readings
> get readings for dashboard 
* GET /api/ownedDevices
> get readings for device dashboard
* DELETE /api/remove
> removes device...
* POST /api/claim
> claims device...
* POST /api/upload
> upload telemetry data from esp32

# Tech stack + information
Spring boot Java 17 Maven C++ Arduino Framework WifiManager by tzapu

I used captive portal (via WiFiManager by Tzapu) to allow end-users to configure Wi-Fi credentials dynamically. 
I did spring security to issue tokens


