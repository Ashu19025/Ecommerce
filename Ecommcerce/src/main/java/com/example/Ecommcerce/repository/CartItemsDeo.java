package com.example.Ecommcerce.repository;

import com.example.Ecommcerce.models.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsDeo extends JpaRepository<CartItem,Integer> {
}
