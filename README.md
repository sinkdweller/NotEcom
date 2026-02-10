# Project Overview # I'm not done yet wait 

This is a mock project for a smart plant-monitoring system you can implement at home. It has the added benefit of detecting housefires! Using modified esp-32 as devices, users are able to register/login to an account to access a jwt authenticated dashboard + device managing/registering page.
Currently the app is hosted locally (docker image is being worked on) so there is a need to enter local server ip address when registering a new monitoring device. The app is initialized with a few mock entities to play with,
but you can add your own. Why did I implement user security if I plan on keeping everything local? for fun

**Seeded credentials**:
* InitialUsername: InitialUsername
* InitialPassword: InitialPassword

# How to use
I will upload building locally later instructions coming
/login.html --> this is the login page
/register.htnk --> this is the register page

Set up device how
build according to this schematic (i shall upload later)
press button to connect (temporary portal will open)
connect to via wifi (called esp capture portal something)
enter your information once--> gets stored in NVS
plug in soil and enjoy


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

I used captive portal (via WiFiManager by Tzapu) to allow end-users to configure Wi-Fi credentials and the server's local IP dynamically. 
I did spring security to issue tokens


