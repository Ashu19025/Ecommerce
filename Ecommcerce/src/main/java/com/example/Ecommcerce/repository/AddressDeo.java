package com.example.Ecommcerce.repository;

import com.example.Ecommcerce.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDeo extends JpaRepository<Address,Integer> {

}
