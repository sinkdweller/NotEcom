package com.example.ecom.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data                 // Generates getters, setters, toString, equals, and hashCode
@AllArgsConstructor
@NoArgsConstructor

public class TelemetryDTO {
    private String macAddress; // "AA:BB:CC:11:22:33"
    private double temperature;
    private double soilMoisture;
}
