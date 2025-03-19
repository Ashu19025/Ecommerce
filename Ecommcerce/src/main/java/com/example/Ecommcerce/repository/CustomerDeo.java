package com.example.Ecommcerce.repository;

import com.example.Ecommcerce.models.CustomerOrder;
import com.example.Ecommcerce.models.customer.Customer;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerDeo extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByMobileNo(String mobileNo);

    Optional<Customer> findByEmailId(String emailId);

    Optional<Customer> findByMobileNoOrEmailId(String mobileNo, String emailId);
}
