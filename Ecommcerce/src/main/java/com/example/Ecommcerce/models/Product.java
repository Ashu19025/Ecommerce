package com.example.Ecommcerce.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    @NotNull
    @Size(min = 3,max = 30,message = "product name size should be between 3-30")
    private String productName;

    @NotNull
    @DecimalMin(value = "0.00")
    private Double price;

    private String description;

    @NotNull
    private String manufacturer;

    @NotNull
    @Min(value = 0)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

//	@ManyToMany(cascade = CascadeType.ALL)
//	private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Seller seller;

//	@ManyToMany
//	@JsonIgnore
//	private List<Cart> productCarts = new ArrayList<>();



}
