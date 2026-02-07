package com.example.ecom.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Entity
@Table(name = "sensor_readings") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorReading {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name="device_id") 
    private Device device; 

    private double temperature;
    private double soilMoisture; 
    private LocalDateTime capturedAt;
}