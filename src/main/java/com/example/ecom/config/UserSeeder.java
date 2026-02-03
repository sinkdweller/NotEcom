package com.example.ecom.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.ecom.repo.UserRepo;
import com.example.ecom.entity.User;
import com.example.ecom.entity.Device;
import com.example.ecom.repo.DeviceRepo;
import com.example.ecom.repo.SensorReadingRepo;
import com.example.ecom.entity.SensorReading;
@Component
public class UserSeeder implements CommandLineRunner{

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final DeviceRepo deviceRepo;
    private final SensorReadingRepo sensorReadingRepo;
    public UserSeeder(UserRepo userRepo,DeviceRepo deviceRepo, PasswordEncoder passwordEncoder, SensorReadingRepo sensorReadingRepo){
        this.userRepo=userRepo;
        this.deviceRepo=deviceRepo;
        this.passwordEncoder=passwordEncoder;
        this.sensorReadingRepo=sensorReadingRepo;
    }

    private final String initUsername = "InitialUsername";
    private final String initPassword = "InitialPassword";

    private final String initDevice = "initialDevice";
    private final String initMacAddress = "initialMacAddress";

    @Override
    public void run(String... args) {
        if(userRepo.findByUsername(initUsername).isEmpty()){
            //SEED USER
            User user = new User();
            user.setUsername(initUsername);
            user.setPassword(passwordEncoder.encode(initPassword));
            userRepo.save(user);
            System.out.println("Database seeded with Username: " + initUsername + " Password: " + initPassword);
            
            //SEED DEVICE
            Device device = new Device();
            device.setUser(user);
            device.setMacAddress(initMacAddress);
            device.setName(initDevice);

            deviceRepo.save(device); 
            System.out.println("Device '" + initDevice + "' seeded for user: " + initUsername);

            //SEED SENSOR READINGS
            for(int i=0; i<10; i++){
                SensorReading reading = new SensorReading();
                reading.setDevice(device);
                reading.setTemperature(20.0 + i); 
                reading.setSoilMoisture(40.0 + i );
                reading.setCapturedAt(LocalDateTime.now().minusHours(i));
                sensorReadingRepo.save(reading);
            }
            System.out.println("Seeded 10 sensor readings for the device!");

        }

    }
    
}
