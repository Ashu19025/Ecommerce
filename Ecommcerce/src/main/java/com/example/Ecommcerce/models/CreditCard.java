package com.example.Ecommcerce.models;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCard {
    @Pattern(regexp = "[0-9]{16,18}", message = "Invalid card number")
    @NotNull
    private String cardNumber;

    @Pattern(regexp = "[0-9]{2}/[0-9]{2,4}", message = "Invalid validity. Enter in MM/YY or MM/YYYY format")
    private String cardValidity;

    @Pattern(regexp = "[0-9]{3}", message = "Invalid CVV. Must be numeric 3 digits length.")
    private String cardCVV;

}
