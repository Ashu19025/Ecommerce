package com.example.Ecommcerce.models;



import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityScan
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sessionId;

    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)

    private String token;

    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    private String userType;

    private LocalDateTime sessionStartTime;

    private LocalDateTime sessionEndTime;
}
