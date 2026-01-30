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
public class DeviceController {
    private final DeviceRepo deviceRepo;
    private final UserRepo userRepo;
    public DeviceController(DeviceRepo deviceRepo, UserRepo userRepo){
        this.deviceRepo=deviceRepo;
        this.userRepo=userRepo;
    }
    @PostMapping("/claim")
public ResponseEntity<String> claimDevice(@RequestBody ClaimDeviceDto dto) {
    System.out.println("1. Starting claim for MAC: " + dto.getMacAddress());

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    User currentUser = userRepo.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found in DB"));

    Device device = deviceRepo.findByMacAddress(dto.getMacAddress())
        .orElseGet(() -> {
            Device d = new Device();
            d.setMacAddress(dto.getMacAddress());
            return d;
        });

    if (device.getUser() != null && !device.getUser().equals(currentUser)) {
        return ResponseEntity.status(409).body("Already claimed by another user.");
    }

    device.setName(dto.getName());
    device.setUser(currentUser);
    
    deviceRepo.save(device);

    return ResponseEntity.ok("Device added!");
}
    
}
