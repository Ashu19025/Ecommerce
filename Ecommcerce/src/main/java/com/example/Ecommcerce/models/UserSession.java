package com.example.Ecommcerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_session") // table names are usually lowercase with underscores
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Only on primary key
    private Integer sessionId;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(unique = true, nullable = false)
    private Integer userId;

    private String userType;

    private LocalDateTime sessionStartTime;

    private LocalDateTime sessionEndTime;

    // Generate token before saving to the database
    @PrePersist
    public void generateToken() {
        if (this.token == null) {
            this.token = UUID.randomUUID().toString();
        }
    }
}
