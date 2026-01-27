package com.example.ecom.repo;

import com.example.ecom.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DeviceRepo extends JpaRepository<Device, Long> {
    // Allows  Service to find the "Kitchen Fern" by its hardware ID
    Optional<Device> findByMacAddress(String macAddress);
}