package com.example.Ecommcerce.repository;

import com.example.Ecommcerce.models.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDeo extends JpaRepository<Cart,Integer> {
}
