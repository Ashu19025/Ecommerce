package com.example.Ecommcerce.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Product Not Found");  // Default message if none is provided
    }

    public ProductNotFoundException(String message) {
        super(message);  // Calls RuntimeException's constructor, enabling getMessage()
    }
}
