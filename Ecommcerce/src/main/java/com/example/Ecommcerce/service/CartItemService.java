package com.example.Ecommcerce.service;

import com.example.Ecommcerce.models.cart.CartDTO;
import com.example.Ecommcerce.models.cart.CartItem;

public interface CartItemService {
    public CartItem createItemforCart(CartDTO cartdto);
}
