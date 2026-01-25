package com.example.ecom.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data                 // Generates getters, setters, toString, equals, and hashCode
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryDTO {
    private double temperature;
    private double humidity;
    
}
