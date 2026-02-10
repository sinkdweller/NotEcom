# Project Overview

This is a mock project for a smart plant-monitoring system you can implement at home. Using modified esp-32 as devices, users are able to register/login to an account to access a jwt authenticated dashboard + device managing/registering page.
Currently the app is hosted locally (docker image is being worked on) so there is a need to enter local server ip address when registering a new monitoring device. The app is initialized with a few mock entities to play with,
but you can add your own. Why did I implement user security if I plan on keeping everything local? for fun . I used Tzapu's WiFiManager extension for internet connection. I'm not done yet im almost there...!

**Seeded credentials**:
* InitialUsername: InitialUsername
* InitialPassword: InitialPassword

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
