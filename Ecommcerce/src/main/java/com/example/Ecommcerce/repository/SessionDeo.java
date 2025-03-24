package com.example.Ecommcerce.repository;

import com.example.Ecommcerce.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SessionDeo extends JpaRepository<UserSession,Long> {

    static Optional<UserSession> findByToken(String token) {
        return null;
    }

    static Optional<UserSession> findByUserId(Integer userId) {
        return null;
    }

}
