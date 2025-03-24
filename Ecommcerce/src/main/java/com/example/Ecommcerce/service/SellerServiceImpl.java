package com.example.Ecommcerce.service;

import com.example.Ecommcerce.exception.LoginException;
import com.example.Ecommcerce.exception.SellerException;
import com.example.Ecommcerce.models.SessionDTO;
import com.example.Ecommcerce.models.UserSession;
import com.example.Ecommcerce.models.seller.Seller;
import com.example.Ecommcerce.models.seller.SellerDTO;
import com.example.Ecommcerce.repository.SellerDeo;
import com.example.Ecommcerce.repository.SessionDeo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService { // Implement the interface

    @Autowired
    private SellerDeo sellerDao;

    @Autowired
    private LoginLogoutService loginService;

    @Autowired
    private SessionDeo sessionDao;

    @Override
    public Seller addSeller(Seller seller) {
        return sellerDao.save(seller);
    }

    @Override
    public List<Seller> getAllSellers() throws SellerException {
        List<Seller> sellers = sellerDao.findAll();
        if (sellers.isEmpty()) {
            throw new SellerException("No Seller Found!");
        }
        return sellers;
    }

    @Override
    public Seller getSellerById(Integer sellerId) throws SellerException {
        return sellerDao.findById(sellerId)
                .orElseThrow(() -> new SellerException("Seller not found for this ID: " + sellerId));
    }

    @Override
    public Seller updateSeller(Seller seller, String token) throws SellerException, LoginException {
        validateSellerToken(token);
        loginService.checkTokenStatus(token);

        Seller existingSeller = sellerDao.findById(seller.getSellerId())
                .orElseThrow(() -> new SellerException("Seller not found for this Id: " + seller.getSellerId()));

        return sellerDao.save(seller);
    }

    @Override
    public Seller deleteSellerById(Integer sellerId, String token) throws SellerException, LoginException {
        validateSellerToken(token);
        loginService.checkTokenStatus(token);

        Seller existingSeller = sellerDao.findById(sellerId)
                .orElseThrow(() -> new SellerException("Seller not found for this ID: " + sellerId));

        UserSession user = SessionDeo.findByToken(token)
                .orElseThrow(() -> new LoginException("Invalid session token"));

        if (user.getUserId() == existingSeller.getSellerId()) {
            sellerDao.delete(existingSeller);

            // Log out the seller after deleting the account
            SessionDTO session = new SessionDTO();
            session.setToken(token);
            loginService.logoutSeller(session);

            return existingSeller;
        } else {
            throw new SellerException("Verification Error in deleting seller account");
        }
    }

    @Override
    public Seller updateSellerMobile(SellerDTO sellerDto, String token) throws SellerException, LoginException {
        validateSellerToken(token);
        loginService.checkTokenStatus(token);

        UserSession user = SessionDeo.findByToken(token)
                .orElseThrow(() -> new LoginException("Invalid session token"));

        Seller existingSeller = sellerDao.findById(user.getUserId())
                .orElseThrow(() -> new SellerException("Seller not found for this ID: " + user.getUserId()));

        if (existingSeller.getPassword().equals(sellerDto.getPassword())) {
            existingSeller.setMobile(sellerDto.getMobile());
            return sellerDao.save(existingSeller);
        } else {
            throw new SellerException("Error occurred in updating mobile. Please enter the correct password");
        }
    }

    @Override
    public Seller getSellerByMobile(String mobile, String token) throws SellerException, LoginException {
        validateSellerToken(token);
        loginService.checkTokenStatus(token);

        return sellerDao.findByMobile(mobile)
                .orElseThrow(() -> new SellerException("Seller not found with given mobile"));
    }

    @Override
    public Seller getCurrentlyLoggedInSeller(String token) throws SellerException, LoginException {
        validateSellerToken(token);
        loginService.checkTokenStatus(token);

        UserSession user = SessionDeo.findByToken(token)
                .orElseThrow(() -> new LoginException("Invalid session token"));

        return sellerDao.findById(user.getUserId())
                .orElseThrow(() -> new SellerException("Seller not found for this ID"));
    }

    @Override
    public SessionDTO updateSellerPassword(SellerDTO sellerDto, String token) throws SellerException, LoginException {
        validateSellerToken(token);
        loginService.checkTokenStatus(token);

        UserSession user = SessionDeo.findByToken(token)
                .orElseThrow(() -> new LoginException("Invalid session token"));

        Seller existingSeller = sellerDao.findById(user.getUserId())
                .orElseThrow(() -> new SellerException("Seller does not exist"));

        if (!sellerDto.getMobile().equals(existingSeller.getMobile())) {
            throw new SellerException("Verification error. Mobile number does not match");
        }

        existingSeller.setPassword(sellerDto.getPassword());
        sellerDao.save(existingSeller);

        SessionDTO session = new SessionDTO();
        session.setToken(token);
        loginService.logoutSeller(session);
        session.setMessage("Updated password and logged out. Login again with new password");

        return session;
    }

    private void validateSellerToken(String token) throws LoginException {
        if (token == null || !token.contains("seller")) {
            throw new LoginException("Invalid session token for seller");
        }
    }
}
