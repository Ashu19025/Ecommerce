package com.example.Ecommcerce.repository;

import com.example.Ecommcerce.models.customer.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerDeo extends JpaRepository<Customer,Integer> {


    Optional<Customer> findByMobileNoOrEmailid(String mobileNo, String emailid);

    Optional<Customer> findByMobileNo(@NotNull(message = "please enter the mobile Number") @Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit mobile number") String mobileNo);

    Optional<Customer> findByEmailid(@Email String emailid);
}
