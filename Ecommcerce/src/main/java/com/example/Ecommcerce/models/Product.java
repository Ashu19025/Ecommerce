package com.example.Ecommcerce.models;


import jakarta.persistence.*;
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


}
