package com.example.Ecommcerce.repository;

import com.example.Ecommcerce.models.seller.Seller;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
@Repository
public interface SellerDeo extends JpaRepository<Seller,Integer> {
    Optional<Seller>findByMobile(String mobile);
}
