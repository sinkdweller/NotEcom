package com.example.ecom.service;
import com.example.ecom.entity.User;
import com.example.ecom.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRegisterServiceTest {

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testUserRegistration() {
        //Data for a new user
        String username = "testuser";
        String rawPassword = "password123";
        String email = "test@example.com";

        // Call the service
        userRegisterService.createUser(username, rawPassword, email);

        // Check the database
        Optional<User> savedUser = userRepo.findByUsername(username);
        
        assertTrue(savedUser.isPresent(), "User should be found in the database");
        assertEquals(email, savedUser.get().getEmail());
        
        // Check if the password was actually encoded
        assertTrue(passwordEncoder.matches(rawPassword, savedUser.get().getPassword()), 
                   "The saved password should match the encoded raw password");
        
        assertNotEquals(rawPassword, savedUser.get().getPassword(), 
                        "The password should NOT be stored in plain text");
    }
}