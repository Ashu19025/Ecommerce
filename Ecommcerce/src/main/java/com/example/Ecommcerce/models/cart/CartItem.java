package com.example.Ecommcerce.models.cart;


import com.example.Ecommcerce.models.CustomerOrder;
import com.example.Ecommcerce.models.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartItemId;

    @OneToOne
    @JsonIgnoreProperties(value ={"productId", "seller", "quality"})
    private Product cartProduct;

    private Integer cartItemQuantity;



    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private CustomerOrder customerOrder;


}
