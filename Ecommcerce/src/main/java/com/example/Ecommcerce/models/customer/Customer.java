package com.example.Ecommcerce.models.customer;

import com.example.Ecommcerce.models.Address;
import com.example.Ecommcerce.models.CreditCard;
import com.example.Ecommcerce.models.Order;
import com.example.Ecommcerce.models.cart.Cart;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer CustomerId;

    @NotNull(message = "First Name cannot be Null")
    @Pattern(regexp = "[A-Za-z.\\s]+",message = "Enter valid character in first name")
    private String firstName;

    @NotNull(message = "please enter the mobile Number")
    @Column(unique = true)
    @Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit mobile number")
    private String mobileNo;

    @NotNull(message = "please enter the email id")
    @Column(name = "email_id")
    @Email
    private String emailid;

    @NotNull(message = "please enter the password")
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can include A-Z, a-z, 0-9, or special characters !@#$%^&*_")
    private String password;

    private LocalDateTime createOn;

    @Embedded
    private CreditCard creditCard;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_address_mapping",
            joinColumns = {
                    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
            })
    private Map<String, Address> address = new HashMap<>();


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Cart customerCart;

    public void setCreatedOn(LocalDateTime now) {
    }

    public void setLastName(@Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in last name") String lastName) {
    }
}
