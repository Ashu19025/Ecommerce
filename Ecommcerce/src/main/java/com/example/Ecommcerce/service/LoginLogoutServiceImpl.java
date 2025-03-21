package com.example.Ecommcerce.service;

import com.example.Ecommcerce.exception.CustomerNotFoundException;
import com.example.Ecommcerce.exception.LoginException;
import com.example.Ecommcerce.exception.SellerNotFoundException;
import com.example.Ecommcerce.models.SessionDTO;
import com.example.Ecommcerce.models.UserSession;
import com.example.Ecommcerce.models.customer.Customer;
import com.example.Ecommcerce.models.customer.CustomerDTO;
import com.example.Ecommcerce.models.seller.Seller;
import com.example.Ecommcerce.models.seller.SellerDTO;
import com.example.Ecommcerce.repository.CustomerDeo;
import com.example.Ecommcerce.repository.SellerDeo;
import com.example.Ecommcerce.repository.SessionDeo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginLogoutServiceImpl implements LoginLogoutService {  // Corrected class name

    @Autowired
    private SessionDeo sessionDao;

    @Autowired
    private CustomerDeo customerDao;

    @Autowired
    private SellerDeo sellerDao;

    @Override
    public UserSession loginCustomer(CustomerDTO loginCustomer) {
        Optional<Customer> res = customerDao.findByMobileNo(loginCustomer.getMobileId());

        if (res.isEmpty()) {
            throw new CustomerNotFoundException("Customer record does not exist with given mobile number");
        }

        Customer existingCustomer = res.get();
        Optional<UserSession> opt = SessionDeo.findByUserId(existingCustomer.getCustomerId());

        if (opt.isPresent()) {
            UserSession user = opt.get();
            if (user.getSessionEndTime().isBefore(LocalDateTime.now())) {
                sessionDao.delete(user);
            } else {
                throw new LoginException("User already logged in");
            }
        }

        if (existingCustomer.getPassword().equals(loginCustomer.getPassword())) {
            UserSession newSession = new UserSession();
            newSession.setUserId(existingCustomer.getCustomerId());
            newSession.setUserType("customer");
            newSession.setSessionStartTime(LocalDateTime.now());
            newSession.setSessionEndTime(LocalDateTime.now().plusHours(1));

            UUID uuid = UUID.randomUUID();
            String token = "customer_" + uuid.toString().split("-")[0];

            newSession.setToken(token);
            return sessionDao.save(newSession);
        } else {
            throw new LoginException("Password Incorrect. Try again.");
        }
    }

    @Override
    public SessionDTO logoutCustomer(SessionDTO sessionToken) {  // Corrected return type
        String token = sessionToken.getToken();
        checkTokenStatus(token);

        Optional<UserSession> opt = SessionDeo.findByToken(token);

        if (opt.isEmpty()) {
            throw new LoginException("User not logged in. Invalid session token. Login Again.");
        }

        sessionDao.delete(opt.get());
        sessionToken.setMessage("Logged out successfully.");
        return sessionToken;
    }

    @Override
    public void checkTokenStatus(String token) {
        Optional<UserSession> opt = SessionDeo.findByToken(token);

        if (opt.isPresent()) {
            UserSession session = opt.get();
            if (session.getSessionEndTime().isBefore(LocalDateTime.now())) {
                sessionDao.delete(session);
                throw new LoginException("Session expired. Login Again");
            }
        } else {
            throw new LoginException("User not logged in. Invalid session token. Please login first.");
        }

        deleteExpiredTokens();
    }

    @Override
    public UserSession loginSeller(SellerDTO seller) {
        Optional<Seller> res = sellerDao.findByMobile(seller.getMobile());

        if (res.isEmpty()) {
            throw new SellerNotFoundException("Seller record does not exist with given mobile number");
        }

        Seller existingSeller = res.get();
        Optional<UserSession> opt = SessionDeo.findByUserId(existingSeller.getSellerId());

        if (opt.isPresent()) {
            UserSession user = opt.get();
            if (user.getSessionEndTime().isBefore(LocalDateTime.now())) {
                sessionDao.delete(user);
            } else {
                throw new LoginException("User already logged in");
            }
        }

        if (existingSeller.getPassword().equals(seller.getPassword())) {
            UserSession newSession = new UserSession();
            newSession.setUserId(existingSeller.getSellerId());
            newSession.setUserType("seller");
            newSession.setSessionStartTime(LocalDateTime.now());
            newSession.setSessionEndTime(LocalDateTime.now().plusHours(1));

            UUID uuid = UUID.randomUUID();
            String token = "seller_" + uuid.toString().split("-")[0];

            newSession.setToken(token);
            return sessionDao.save(newSession);
        } else {
            throw new LoginException("Password Incorrect. Try again.");
        }
    }

    @Override
    public SessionDTO logoutSeller(SessionDTO session) {
        String token = session.getToken();
        checkTokenStatus(token);

        Optional<UserSession> opt = SessionDeo.findByToken(token);

        if (opt.isEmpty()) {
            throw new LoginException("User not logged in. Invalid session token. Login Again.");
        }

        sessionDao.delete(opt.get());
        session.setMessage("Logged out successfully.");
        return session;
    }

    @Override
    public void deleteExpiredTokens() {
        List<UserSession> users = sessionDao.findAll();

        if (!users.isEmpty()) {
            for (UserSession user : users) {
                if (user.getSessionEndTime().isBefore(LocalDateTime.now())) {
                    sessionDao.delete(user);
                }
            }
        }
    }
}
