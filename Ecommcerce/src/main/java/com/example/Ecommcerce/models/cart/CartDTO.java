package com.example.Ecommcerce.models.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDTO {
    @NotNull
    private Integer productId;

    private String productName;

    private Double price;

    @Min(1)
    private Integer quantity;

    public CartDTO(Integer productId) {

    }
}
