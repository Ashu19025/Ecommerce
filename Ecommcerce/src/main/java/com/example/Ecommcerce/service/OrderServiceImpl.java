package com.example.Ecommcerce.service;

import com.example.Ecommcerce.exception.LoginException;
import com.example.Ecommcerce.exception.OrderException;
import com.example.Ecommcerce.models.Order;
import com.example.Ecommcerce.models.OrderDTO;
import com.example.Ecommcerce.models.cart.CartDTO;
import com.example.Ecommcerce.models.cart.CartItem;
import com.example.Ecommcerce.models.customer.Customer;
import com.example.Ecommcerce.models.product.ProductStatus;
import com.example.Ecommcerce.repository.OrderDeo; // Retained as OrderDeo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl {


    @Autowired
    private OrderDeo orderDeo; // Retained as OrderDeo

    @Autowired
    private CustomerService cs;

    @Autowired
    private CartServiceImpl cartservice;

    public enum OrderStatus { // Added OrderStatus enum
        SUCCESS,
        PENDING,
        CANCELLED
    }

    public Order saveOrder(OrderDTO odto, String token) throws LoginException, OrderException {
        Order newOrder = new Order();
        Customer loggedInCustomer = cs.getLoggedInCustomerDetails(token);

        if (loggedInCustomer == null) {
            throw new LoginException("Invalid session token. Kindly Login.");
        }

        newOrder.setCustomer(loggedInCustomer);
        List<CartItem> productsInCart = loggedInCustomer.getCustomerCart().getCartItems();

        if (productsInCart.isEmpty()) {
            throw new OrderException("No products in Cart");
        }

        if (loggedInCustomer.getCreditCard() == null || loggedInCustomer.getAddress() == null) {
            throw new OrderException("Customer credit card or address is not set.");
        }

        String usersCardNumber = loggedInCustomer.getCreditCard().getCardNumber();
        String userGivenCardNumber = odto.getCardNumber().getCardNumber();


        if (usersCardNumber.equals(userGivenCardNumber) &&
                odto.getCardNumber().getCardValidity().equals(loggedInCustomer.getCreditCard().getCardValidity()) &&
                odto.getCardNumber().getCardCVV().equals(loggedInCustomer.getCreditCard().getCardCVV())) {

            newOrder.setCardNumber(userGivenCardNumber);
            newOrder.setAddress(loggedInCustomer.getAddress().get(odto.getAddressType()));
            newOrder.setDate(LocalDate.now());
            newOrder.setOrderStatus(OrderStatus.SUCCESS);

            for (CartItem cartItem : productsInCart) {
                int remainingQuantity = cartItem.getCartProduct().getQuantity() - cartItem.getCartItemQuantity();
                if (remainingQuantity < 0 || cartItem.getCartProduct().getStatus() == ProductStatus.OUTOFSTOCK) {
                    cartservice.removeProductFromCart(new CartDTO(cartItem.getCartProduct().getProductId()), token);
                    throw new OrderException("Product " + cartItem.getCartProduct().getProductName() + " OUT OF STOCK");
                }
                cartItem.getCartProduct().setQuantity(remainingQuantity);
                if (remainingQuantity == 0) {
                    cartItem.getCartProduct().setStatus(ProductStatus.OUTOFSTOCK);
                }
            }
            cartservice.clearCart(token);
            return orderDeo.save(newOrder);
        } else {
            newOrder.setOrderStatus(OrderStatus.PENDING);
            return orderDeo.save(newOrder);
        }
    }

    public Order getOrderByOrderId(Integer orderId) throws OrderException {
        return orderDeo.findById(orderId).orElseThrow(() -> new OrderException("No order exists with ID: " + orderId));
    }

    public List<Order> getAllOrders() throws OrderException {
        List<Order> orders = orderDeo.findAll();
        if (!orders.isEmpty()) return orders;
        throw new OrderException("No orders exist.");
    }

    public List<Order> getAllOrdersByDate(LocalDate date) throws OrderException {
        return orderDeo.findOrdersByDate(date);
    }

    public Customer getCustomerByOrderId(Integer orderId) throws OrderException {
        return orderDeo.getCustomerByOrderId(orderId);
    }
}
