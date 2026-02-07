package com.example.ecom.responses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data                 // Generates getters, setters, toString, equals, and hashCode
@AllArgsConstructor  
@NoArgsConstructor
public class SensorReadingResponse {
    private String deviceName;
    private double moisture;
    private double temperature;
    private LocalDateTime capturedAt; 
}
