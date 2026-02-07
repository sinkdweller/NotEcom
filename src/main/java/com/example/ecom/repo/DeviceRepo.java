package com.example.ecom.repo;

import com.example.ecom.entity.Device;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface DeviceRepo extends JpaRepository<Device, Long> {

    List<Device> findAllByUserId(Long userId);
    
    Optional<Device> findByMacAddress(String macAddress);
    @Transactional
    @Modifying
    void deleteByMacAddress(String macAddress);
}