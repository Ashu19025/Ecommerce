package com.example.Ecommcerce.service;

import com.example.Ecommcerce.exception.SellerException;
import com.example.Ecommcerce.models.CategoryEnum;
import com.example.Ecommcerce.models.product.Product;
import com.example.Ecommcerce.models.product.ProductDTO;
import com.example.Ecommcerce.models.product.ProductStatus;

import java.util.List;

public interface ProductService {
    public Product addProductToCatalog(String token, Product product) throws SellerException;

    public Product getProductFromCatalogById(Integer id);

    public String deleteProductFromCatalog(Integer id);

    public Product updateProductIncatalog(Product product);

    public List<Product> getAllProductsIncatalog();

    public List<ProductDTO> getAllProductsOfSeller(Integer id);

    public List<ProductDTO> getProductsOfCategory(CategoryEnum catenum);

    public List<ProductDTO> getProductsOfStatus(ProductStatus status);



    public Product updateProductQuantityWithId(Integer id,ProductDTO prodDTO);
}
