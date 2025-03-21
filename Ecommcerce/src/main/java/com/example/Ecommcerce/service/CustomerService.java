package com.example.Ecommcerce.service;

import com.example.Ecommcerce.exception.CustomerException;
import com.example.Ecommcerce.exception.CustomerNotFoundException;
import com.example.Ecommcerce.models.Address;
import com.example.Ecommcerce.models.CreditCard;
import com.example.Ecommcerce.models.Order;
import com.example.Ecommcerce.models.SessionDTO;
import com.example.Ecommcerce.models.customer.Customer;
import com.example.Ecommcerce.models.customer.CustomerDTO;
import com.example.Ecommcerce.models.customer.CustomerUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CustomerService {
    public Customer addCustomer(Customer customer) throws CustomerException;

    public Customer getLoggedInCustomerDetails(String token) throws CustomerNotFoundException;

    public List<Customer> getAllCustomers(String token) throws CustomerNotFoundException;

    public Customer updateCustomer(CustomerUpdateDTO customer, String token) throws CustomerNotFoundException;

    public Customer updateCustomerMobileNoOrEmailId(CustomerUpdateDTO customerUpdateDTO, String token) throws CustomerNotFoundException;

    public Customer updateCreditCardDetails(String token, CreditCard card) throws CustomerException;

    Customer updateCreditCarddetails(String token, CreditCard card) throws CustomerException;

    SessionDTO updateCustomerpassword(CustomerDTO customerDTO, String token) throws CustomerNotFoundException;

    public SessionDTO updateCustomerPassword(CustomerDTO customerDTO, String token) throws CustomerNotFoundException;

    public SessionDTO deleteCustomer(CustomerDTO customerDTO, String token) throws CustomerNotFoundException;

    public Customer updateAddress(Address address, String type, String token) throws CustomerException;

    Customer updateaddress(Address address, String type, String token) throws CustomerException;

    public Customer deleteAddress(String type, String token) throws CustomerException, CustomerNotFoundException;

    public List<Order> getCustomerOrders(String token) throws CustomerException;
}
