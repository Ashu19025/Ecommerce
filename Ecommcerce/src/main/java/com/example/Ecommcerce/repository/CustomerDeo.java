package com.example.Ecommcerce.repository;

import com.example.Ecommcerce.models.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerDeo extends JpaRepository<Customer,Integer> {


    Optional<Customer> findByMobileNoOrEmailid(String mobileNo, String emailid);
}
