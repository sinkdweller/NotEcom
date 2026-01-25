package com.example.ecom.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.dtos.TelemetryDTO;
import com.example.ecom.entity.SensorReading;
import com.example.ecom.entity.User;
import com.example.ecom.repo.SensorReadingRepo;
import com.example.ecom.repo.UserRepo;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class TelemetryController {

    private SensorReadingRepo sensorReadingRepo;
    private UserRepo userRepo;
    public TelemetryController(SensorReadingRepo sensorReadingRepo, UserRepo userRepo){
        this.sensorReadingRepo=sensorReadingRepo;
        this.userRepo = userRepo;
    }
    @PostMapping("/upload")
    @Transactional
    public ResponseEntity<String> postTelemetry(@RequestBody TelemetryDTO telemetryDTO){

        // Get the authentication object
        String username = SecurityContextHolder.getContext().getAuthentication().getName();    
        SensorReading sensorData = new SensorReading();
        User currentUser = userRepo.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found: " + username));

        sensorData.setHumidity(telemetryDTO.getHumidity());
        sensorData.setTemperature(telemetryDTO.getTemperature());
        sensorData.setCapturedAt(LocalDateTime.now());
        sensorData.setUser(currentUser);
        sensorReadingRepo.save(sensorData);
        System.out.println("Received sensorData!");
        return ResponseEntity.ok("Data Logged for device: " + currentUser.getUsername());
    }
}
