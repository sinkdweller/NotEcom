package com.example.ecom.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Table(name = "sensor_readings") 
@Data                 // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor    // Generates the empty constructor User() {}
@AllArgsConstructor   // Generates the full constructor User
@Entity
public class SensorReading {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private double temperature;
    private double humidity;
    private LocalDateTime capturedAt;



}
