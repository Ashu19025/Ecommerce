package com.example.Ecommcerce.service;

import com.example.Ecommcerce.models.SessionDTO;
import com.example.Ecommcerce.models.UserSession;
import com.example.Ecommcerce.models.customer.CustomerDTO;
import com.example.Ecommcerce.models.seller.SellerDTO;

public interface LoginLogoutService {
    UserSession loginCustomer(CustomerDTO customer);

    SessionDTO logoutCustomer(SessionDTO session); // Updated return type

    void checkTokenStatus(String token);

    void deleteExpiredTokens();

    UserSession loginSeller(SellerDTO seller);

    SessionDTO logoutSeller(SessionDTO session);
}
