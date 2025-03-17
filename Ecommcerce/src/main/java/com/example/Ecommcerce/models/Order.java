package com.example.Ecommcerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy  = GenerationType.AUTO)
    private Integer orderId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatusValue orderStatus;
    private Double total;
    private String cardNumber;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cutomer_id",referencedColumnName = "customerId")
    private Customer customer;

    @OneToMany
    private List<CartItem> ordercartitems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "address_id",referencedColumnName = "addressId")
    private Address address;

}
