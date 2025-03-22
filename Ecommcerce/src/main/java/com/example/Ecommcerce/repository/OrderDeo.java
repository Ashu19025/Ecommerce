package com.example.Ecommcerce.repository;

import com.example.Ecommcerce.models.Order;
import com.example.Ecommcerce.models.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderDeo extends JpaRepository<Order,Integer> {

    @Query("SELECT o.customer FROM Order o WHERE o.id = :orderId")
    Customer getCustomerByOrderid(@Param("orderId") Integer orderId);

    List<Order> findOrdersByDate(LocalDate date);

    Customer getCustomerByOrderId(Integer orderId);
}
