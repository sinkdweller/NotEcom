package com.example.ecom.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.ecom.repo.UserRepo;
import com.example.ecom.entity.User;
import com.example.ecom.entity.Device;
import com.example.ecom.repo.DeviceRepo;
@Component
public class UserSeeder implements CommandLineRunner{

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final DeviceRepo deviceRepo;
    public UserSeeder(UserRepo userRepo,DeviceRepo deviceRepo, PasswordEncoder passwordEncoder){
        this.userRepo=userRepo;
        this.deviceRepo=deviceRepo;
        this.passwordEncoder=passwordEncoder;
    }

    private final String initUsername = "InitialUsername";
    private final String initPassword = "InitialPassword";

    private final String initDevice = "initialDevice";
    private final String initMacAddress = "initialMacAddress";

    @Override
    public void run(String... args) {
        if(userRepo.findByUsername(initUsername).isEmpty()){
            User user = new User();
            user.setUsername(initUsername);
            user.setPassword(passwordEncoder.encode(initPassword));
            userRepo.save(user);
            System.out.println("Database seeded with Username: " + initUsername + " Password: " + initPassword);
            
            Device device = new Device();
            device.setUser(user);
            device.setMacAddress(initMacAddress);
            device.setName(initDevice);

            deviceRepo.save(device); // 3. MUST SAVE the device to the database
            System.out.println("Device '" + initDevice + "' seeded for user: " + initUsername);
        }

    }
    
}
