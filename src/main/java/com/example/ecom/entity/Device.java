package com.example.ecom.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devices") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String macAddress; 
    private String name;       // e.g., "Kitchen Fern"
}