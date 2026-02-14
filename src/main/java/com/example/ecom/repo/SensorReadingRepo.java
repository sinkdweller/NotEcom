package com.example.ecom.repo;
import com.example.ecom.entity.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface SensorReadingRepo extends JpaRepository<SensorReading, Long>{
    Page<SensorReading> findByDevice_User_Username(String username, Pageable pageable);
}