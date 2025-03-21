package com.example.Ecommcerce.service;

import com.example.Ecommcerce.exception.ProductNotFoundException;
import com.example.Ecommcerce.models.cart.CartDTO;
import com.example.Ecommcerce.models.cart.CartItem;
import com.example.Ecommcerce.models.product.Product;
import com.example.Ecommcerce.models.product.ProductStatus;
import com.example.Ecommcerce.repository.ProductDeo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private ProductDeo productDeo;

    @Override
    public CartItem createItemforCart(CartDTO cartdto) {
        System.out.println("Fetching product with ID: " + cartdto.getProductId());

        Product existingProduct = productDeo.findById(cartdto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product Not found"));

        int productQuantity = existingProduct.getQuantity() == null ? 0 : existingProduct.getQuantity();

        if (ProductStatus.OUTOFSTOCK.equals(existingProduct.getStatus()) || productQuantity == 0) {
            throw new ProductNotFoundException("Product OUT OF STOCK");
        }

        CartItem newItem = new CartItem();
        newItem.setCartItemQuantity(1);
        newItem.setCartProduct(existingProduct);

        return newItem;
    }
}
