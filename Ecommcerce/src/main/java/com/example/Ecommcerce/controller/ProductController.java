package com.example.Ecommcerce.controller;

import com.example.Ecommcerce.exception.SellerException;
import com.example.Ecommcerce.models.CategoryEnum;
import com.example.Ecommcerce.models.product.Product;
import com.example.Ecommcerce.models.product.ProductDTO;
import com.example.Ecommcerce.models.product.ProductStatus;
import com.example.Ecommcerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService pService;



    @PostMapping("/products")
    public ResponseEntity<Product> addProductToCatalogHandler(@RequestHeader("token") String token,
                                                              @Valid @RequestBody Product product) throws SellerException {

        Product prod = pService.addProductToCatalog(token, product);

        return new ResponseEntity<Product>(prod, HttpStatus.ACCEPTED);

    }



    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductFromCatalogByIdHandler(@PathVariable("id") Integer id) {

        Product prod = pService.getProductFromCatalogById(id);

        return new ResponseEntity<Product>(prod, HttpStatus.FOUND);

    }



    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductFromCatalogHandler(@PathVariable("id") Integer id) {

        String res = pService.deleteProductFromCatalog(id);
        return new ResponseEntity<String>(res, HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProductInCatalogHandler(@Valid @RequestBody Product prod) {

        Product prod1 = pService.updateProductIncatalog(prod);

        return new ResponseEntity<Product>(prod1, HttpStatus.OK);

    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProductsHandler() {

        List<Product> list = pService.getAllProductsIncatalog();

        return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
    }

    //this method gets the products mapped to a particular seller
    @GetMapping("/products/seller/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsOfSellerHandler(@PathVariable("id") Integer id) {

        List<ProductDTO> list = pService.getAllProductsOfSeller(id);

        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);
    }

    @GetMapping("/products/{catenum}")
    public ResponseEntity<List<ProductDTO>> getAllProductsInCategory(@PathVariable("catenum") String catenum) {
        CategoryEnum ce = CategoryEnum.valueOf(catenum.toUpperCase());
        List<ProductDTO> list = pService.getProductsOfCategory(ce);
        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);

    }

    @GetMapping("/products/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsWithStatusHandler(@PathVariable("status") String status) {

        ProductStatus ps = ProductStatus.valueOf(status.toUpperCase());
        List<ProductDTO> list = pService.getProductsOfStatus(ps);

        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);

    }


    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateQuantityOfProduct(@PathVariable("id") Integer id,@RequestBody ProductDTO prodDto){

        Product prod =   pService.updateProductQuantityWithId(id, prodDto);

        return new ResponseEntity<Product>(prod,HttpStatus.ACCEPTED);
    }

}
