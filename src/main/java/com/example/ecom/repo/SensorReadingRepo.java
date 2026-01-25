package com.example.ecom.repo;
import com.example.ecom.entity.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SensorReadingRepo extends JpaRepository<SensorReading, Long>{
}