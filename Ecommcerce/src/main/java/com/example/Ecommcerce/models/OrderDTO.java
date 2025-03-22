package com.example.Ecommcerce.models;


import com.example.Ecommcerce.models.customer.Customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderDTO {
    @NotNull
    @Embedded
    private CreditCard cardNumber;
    @NotNull
    private String addressType;
    @ManyToOne
    @JoinColumn(name = "customer_id") // Ensure the column name matches the database
    private Customer customer;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
