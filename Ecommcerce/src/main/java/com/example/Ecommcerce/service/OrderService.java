package com.example.Ecommcerce.service;

import com.example.Ecommcerce.exception.LoginException;
import com.example.Ecommcerce.exception.OrderException;
import com.example.Ecommcerce.models.Order;
import com.example.Ecommcerce.models.OrderDTO;
import com.example.Ecommcerce.models.customer.Customer;


import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    public Order saveOrder(OrderDTO odto, String token) throws LoginException, OrderException;

    public Order getOrderByOrderId(Integer OrderId) throws OrderException;

    public List<Order> getAllOrders() throws OrderException;

    public Order cancelOrderByOrderId(Integer OrderId,String token) throws OrderException;

    public Order updateOrderByOrder(OrderDTO order,Integer OrderId,String token) throws OrderException,LoginException;

    public List<Order> getAllOrdersByDate(LocalDate date) throws OrderException;

    public Customer getCustomerByOrderid(Integer orderId) throws OrderException;


    Customer getCustomerByOrderId(Integer orderId) throws OrderException;
}
