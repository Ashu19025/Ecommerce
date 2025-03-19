package com.example.Ecommcerce.models;

import com.example.Ecommcerce.models.cart.CartItem;
import com.example.Ecommcerce.models.customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatusValue orderStatus;

    private Double total;
    private String cardNumber;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")  // ✅ Fixed typo
    private Customer customer;



    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItem> ordercartitems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
    private Address address;

}
