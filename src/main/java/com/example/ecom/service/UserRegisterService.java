package com.example.ecom.service;
import com.example.ecom.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ecom.repo.UserRepo;

@Service
public class UserRegisterService {
    private PasswordEncoder passwordEncoder;
    private UserRepo userRepo;
    public UserRegisterService(PasswordEncoder passwordEncoder, UserRepo userRepo){
        this.passwordEncoder=passwordEncoder;
        this.userRepo = userRepo;
    }
    public void createUser(String username, String rawPassword, String email) {
        User user = new User();
        user.setUsername(username);
        
        String hashedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(hashedPassword);
        
        user.setEmail(email);
        userRepo.save(user);
    }
}
