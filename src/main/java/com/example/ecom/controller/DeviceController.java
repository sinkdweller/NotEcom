package com.example.ecom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecom.entity.User;
import com.example.ecom.dtos.ClaimDeviceDto;
import com.example.ecom.entity.Device;
import com.example.ecom.repo.DeviceRepo;
import com.example.ecom.repo.UserRepo;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceRepo deviceRepo;
    private final UserRepo userRepo;
    public DeviceController(DeviceRepo deviceRepo, UserRepo userRepo){
        this.deviceRepo=deviceRepo;
        this.userRepo=userRepo;
    }
    @PostMapping("/claim")
    public ResponseEntity<String> claimDevice(@RequestBody ClaimDeviceDto claimDeviceDto){

        //Get the logged-in username from the JWT/Security Context
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        Device device = deviceRepo.findByMacAddress(claimDeviceDto.getMacAddress())
            .map(existingDevice -> {
                // case device is already owned
                if (existingDevice.getUser() != null) {
                    throw new RuntimeException("This device is already claimed.");
                }
                return existingDevice;
            })
            .orElseGet(() -> {
                // case device is brand new
                Device d = new Device();
                d.setMacAddress(claimDeviceDto.getMacAddress());
                return d;
            });
            device.setName(claimDeviceDto.getName());
            device.setUser(currentUser);

            deviceRepo.save(device);
        return ResponseEntity.ok("Device added!");

    }
    
}
