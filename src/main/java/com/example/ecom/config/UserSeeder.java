package com.example.ecom.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.ecom.repo.UserRepo;
import com.example.ecom.entity.User;
@Component

public class UserSeeder implements CommandLineRunner{

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserSeeder(UserRepo userRepo, PasswordEncoder passwordEncoder){
        this.userRepo=userRepo;
        this.passwordEncoder=passwordEncoder;
    }

    private final String initUsername = "InitialUsername";
    private final String initPassword = "InitialPassword";
    private final String initEmail = "InitialEmail@email.com";


    @Override
    public void run(String... args) {
        if(userRepo.findByUsername(initUsername).isEmpty()){
            User user = new User();
            user.setUsername(initUsername);
            user.setPassword(passwordEncoder.encode(initPassword));
            user.setEmail(initEmail);
            userRepo.save(user);
            System.out.println("Database seeded with Username: " + initUsername + " Password: " + initPassword);
        }
    }
    
}
