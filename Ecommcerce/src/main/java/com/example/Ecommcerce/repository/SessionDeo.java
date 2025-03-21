package com.example.Ecommcerce.repository;

import com.example.Ecommcerce.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionDeo extends JpaRepository<UserSession,Long> {
    public static Optional<UserSession> findByToken(String token) {
        return null;
    }

    static Optional<UserSession> findByUserId(Integer Userid) {
        return null;
    }
}
