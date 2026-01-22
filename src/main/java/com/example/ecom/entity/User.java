package com.example.ecom.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "users") 
@Data                 // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor    // Generates the empty constructor User() {}
@AllArgsConstructor   // Generates the full constructor User
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String email;

    
}
