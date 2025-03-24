package com.example.Ecommcerce.controller;

import com.example.Ecommcerce.exception.SellerException;
import com.example.Ecommcerce.models.SessionDTO;
import com.example.Ecommcerce.models.seller.Seller;
import com.example.Ecommcerce.models.seller.SellerDTO;
import com.example.Ecommcerce.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerContoller {
    @Autowired
    private SellerService sService;



    @PostMapping("/addseller")
    public ResponseEntity<Seller> addSellerHandler(@Valid @RequestBody Seller seller){

        Seller addseller=sService.addSeller(seller);

        System.out.println("Seller"+ seller);

        return new ResponseEntity<Seller>(addseller, HttpStatus.CREATED);
    }



    //Get the list of seller-----------------------


    @GetMapping("/sellers")
    public ResponseEntity<List<Seller>> getAllSellerHandler() throws SellerException {

        List<Seller> sellers=sService.getAllSellers();

        return new ResponseEntity<List<Seller>>(sellers, HttpStatus.OK);
    }


    //Get the seller by Id............................


    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Seller> getSellerByIdHandler(@PathVariable("sellerId") Integer Id) throws SellerException {

        Seller getSeller=sService.getSellerById(Id);

        return new ResponseEntity<Seller>(getSeller, HttpStatus.OK);
    }


    // Get Seller by mobile Number

    @GetMapping("/seller")
    public ResponseEntity<Seller> getSellerByMobileHandler(@RequestParam("mobile") String mobile, @RequestHeader("token") String token) throws SellerException {

        Seller getSeller=sService.getSellerByMobile(mobile, token);

        return new ResponseEntity<Seller>(getSeller, HttpStatus.OK);
    }


    // Get currently logged in seller

    @GetMapping("/seller/current")
    public ResponseEntity<Seller> getLoggedInSellerHandler(@RequestHeader("token") String token) throws SellerException {

        Seller getSeller = sService.getCurrentlyLoggedInSeller(token);

        return new ResponseEntity<Seller>(getSeller, HttpStatus.OK);
    }

    //Update the seller..............................


    @PutMapping("/seller")
    public ResponseEntity<Seller> updateSellerHandler(@RequestBody Seller seller, @RequestHeader("token") String token) throws SellerException {
        Seller updatedseller=sService.updateSeller(seller, token);

        return new ResponseEntity<Seller>(updatedseller,HttpStatus.ACCEPTED);

    }


    @PutMapping("/seller/update/mobile")
    public ResponseEntity<Seller> updateSellerMobileHandler(@Valid @RequestBody SellerDTO sellerdto, @RequestHeader("token") String token) throws SellerException {
        Seller updatedseller=sService.updateSellerMobile(sellerdto, token);

        return new ResponseEntity<Seller>(updatedseller,HttpStatus.ACCEPTED);

    }


    @PutMapping("/seller/update/password")
    public ResponseEntity<SessionDTO> updateSellerPasswordHandler(@Valid @RequestBody SellerDTO sellerDto, @RequestHeader("token") String token) throws SellerException {
        return new ResponseEntity<>(sService.updateSellerPassword(sellerDto, token), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/seller/{sellerId}")
    public ResponseEntity<Seller> deleteSellerByIdHandler(@PathVariable("sellerId") Integer Id, @RequestHeader("token") String token) throws SellerException {

        Seller deletedSeller=sService.deleteSellerById(Id, token);

        return new ResponseEntity<Seller>(deletedSeller,HttpStatus.OK);

    }
}
