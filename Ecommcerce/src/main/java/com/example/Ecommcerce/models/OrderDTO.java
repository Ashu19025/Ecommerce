package com.example.Ecommcerce.models;


import com.example.Ecommcerce.models.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
