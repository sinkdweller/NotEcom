package com.example.ecom.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecom.entity.Device;
import com.example.ecom.entity.PendClaims;
import com.example.ecom.repo.DeviceRepo;
import com.example.ecom.dtos.TelemetryDTO;
import com.example.ecom.dtos.PendClaimDto;

import com.example.ecom.entity.SensorReading;
import com.example.ecom.repo.SensorReadingRepo;
import com.example.ecom.repo.PendClaimsRepo;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class TelemetryController {

    private SensorReadingRepo sensorReadingRepo;
    private DeviceRepo deviceRepo;
    private PendClaimsRepo pendClaimsRepo;
    public TelemetryController(SensorReadingRepo sensorReadingRepo, DeviceRepo deviceRepo, PendClaimsRepo pendClaimsrepo){
        this.sensorReadingRepo=sensorReadingRepo;
        this.deviceRepo = deviceRepo;
        this.pendClaimsRepo=pendClaimsrepo;
    }
    @PostMapping("/device/pendClaim")
    public ResponseEntity<String> pendClaim(@RequestBody PendClaimDto dto){
        Optional<PendClaims> existing = pendClaimsRepo.findByMacAddress(dto.getMacAddress());
            
            PendClaims claimToSave;
            if (existing.isPresent()) {
                // Update the code if the device rebooted or regenerated it
                claimToSave = existing.get();
                claimToSave.setGeneratedCode(dto.getGeneratedCode());
            } else {
                // Create a new record
                claimToSave = new PendClaims();
                claimToSave.setMacAddress(dto.getMacAddress());
                claimToSave.setGeneratedCode(dto.getGeneratedCode());
            }

            pendClaimsRepo.save(claimToSave);
            return ResponseEntity.ok("Claim request registered! Awaiting user verification");
    }

    @PostMapping("/device/upload")
    @Transactional
    public ResponseEntity<String> postTelemetry(@RequestBody TelemetryDTO telemetryDTO) {

        //Get the logged-in username from the JWT/Security Context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //make sure this device exists in  DB first
        Device device = deviceRepo.findByMacAddress(telemetryDTO.getMacAddress())
            .orElseThrow(() -> new RuntimeException("Device not found with MAC: " + telemetryDTO.getMacAddress()));

        // Does this device actually belong to the logged-in user
        if (!device.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("You do not own this device.");
        }

        //Map DTO to Entity
        SensorReading sensorData = new SensorReading();
        sensorData.setDevice(device); // Link the reading to the Device
        sensorData.setTemperature(telemetryDTO.getTemperature());
        sensorData.setSoilMoisture(telemetryDTO.getSoilMoisture()); 
        sensorData.setCapturedAt(LocalDateTime.now());

        // Save the reading
        sensorReadingRepo.save(sensorData);

        System.out.println("Received sensorData for device: " + device.getName());
        return ResponseEntity.ok("Data Logged for:d " + device.getName());
    }
    }
