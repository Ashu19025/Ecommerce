package com.example.Ecommcerce.service;

import com.example.Ecommcerce.exception.CartItemNotFound;
import com.example.Ecommcerce.exception.CustomerNotFoundException;
import com.example.Ecommcerce.exception.LoginException;
import com.example.Ecommcerce.models.UserSession;
import com.example.Ecommcerce.models.cart.Cart;
import com.example.Ecommcerce.models.cart.CartDTO;
import com.example.Ecommcerce.models.cart.CartItem;
import com.example.Ecommcerce.models.customer.Customer;
import com.example.Ecommcerce.repository.CartDeo;
import com.example.Ecommcerce.repository.CustomerDeo;
import com.example.Ecommcerce.repository.ProductDeo;
import com.example.Ecommcerce.repository.SessionDeo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDeo cartDeo;

    @Autowired
    private SessionDeo sessionDeo;

    @Autowired
    private CartItemService cartItemService;


    @Autowired
    private CustomerDeo customerDeo;

    @Autowired
    private LoginLogoutService loginService;


    @Autowired
    private ProductDeo productDeo;

    @Override
    public Cart addProductToCart(CartDTO cartDto, String token) {


        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = SessionDeo.findByToken(token).get();

        Optional<Customer> opt = customerDeo.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        Cart customerCart = existingCustomer.getCustomerCart();

        List<CartItem> cartItems = customerCart.getCartItems();

        CartItem item = cartItemService.createItemforCart(cartDto);


        if(cartItems.size() == 0) {
            cartItems.add(item);
            customerCart.setCartTotal(item.getCartProduct().getPrice());
        }
        else {
            boolean flag = false;
            for(CartItem c: cartItems) {
                if(c.getCartProduct().getProductId() == cartDto.getProductId()) {
                    c.setCartItemQuantity(c.getCartItemQuantity() + 1);
                    customerCart.setCartTotal(customerCart.getCartTotal() + c.getCartProduct().getPrice());
                    flag = true;
                }
            }
            if(!flag) {
                cartItems.add(item);
                customerCart.setCartTotal(customerCart.getCartTotal() + item.getCartProduct().getPrice());
            }
        }

        return cartDeo.save(existingCustomer.getCustomerCart());


    }



    @Override
    public Cart getCartProduct(String token) {

        System.out.println("Inside get cart");

        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = SessionDeo.findByToken(token).get();

        Optional<Customer> opt = customerDeo.findById(user.getUserId());


        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

//		System.out.println(existingCustomer);
//		
//		System.out.println(existingCustomer.getCustomerCart());
//		
//		System.out.println("Here reached");
//		
        Integer cartId = existingCustomer.getCustomerCart().getCartId();


        Optional<Cart> optCart= cartDeo.findById(cartId);

        if(optCart.isEmpty()) {
            throw new CartItemNotFound("cart Not found by Id");
        }
//		return optCart.get().getProducts();

        return optCart.get();
//		return cart.getProducts();
    }



    @Override
    public Cart removeProductFromCart(CartDTO cartDto, String token) {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = SessionDeo.findByToken(token).get();

        Optional<Customer> opt = customerDeo.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        Cart customerCart = existingCustomer.getCustomerCart();

        List<CartItem> cartItems = customerCart.getCartItems();

        if(cartItems.size() == 0) {
            throw new CartItemNotFound("Cart is empty");
        }


        boolean flag = false;

        for(CartItem c: cartItems) {
            System.out.println("Item" + c.getCartProduct());
            if(c.getCartProduct().getProductId() == cartDto.getProductId()) {
                c.setCartItemQuantity(c.getCartItemQuantity() - 1);

                customerCart.setCartTotal(customerCart.getCartTotal() - c.getCartProduct().getPrice());
                if(c.getCartItemQuantity() == 0) {

                    cartItems.remove(c);


                    return cartDeo.save(customerCart);
                }
                flag = true;
            }
        }

        if(!flag) {
            throw new CartItemNotFound("Product not added to cart");
        }

        if(cartItems.size() == 0) {
            cartDeo.save(customerCart);
            throw new CartItemNotFound("Cart is empty now");
        }

        return cartDeo.save(customerCart);
    }




    // Method to clear entire cart

    @Override
    public Cart clearCart(String token) {

        if(!token.contains("customer")) {
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = SessionDeo.findByToken(token).get();

        Optional<Customer> opt = customerDeo.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        Cart customerCart = existingCustomer.getCustomerCart();

        if(customerCart.getCartItems().isEmpty()) {
            throw new CartItemNotFound("Cart already empty");
        }

        List<CartItem> emptyCart = new ArrayList<>();

        customerCart.setCartItems(emptyCart);

        customerCart.setCartTotal(0.0);

        return cartDeo.save(customerCart);
    }

}
