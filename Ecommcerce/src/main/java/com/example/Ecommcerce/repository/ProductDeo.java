package com.example.Ecommcerce.repository;

import com.example.Ecommcerce.models.CategoryEnum;
import com.example.Ecommcerce.models.product.Product;
import com.example.Ecommcerce.models.product.ProductDTO;
import com.example.Ecommcerce.models.product.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductDeo extends JpaRepository<Product,Integer> {
    @Query("select new com.example.Ecommcerce.models.product.ProductDTO(p.productName, p.manufacturer, p.price, p.quantity) "
            + "from Product p where p.category=:catenum")
    public List<ProductDTO> getAllProductsInACategory(@Param("catenum") CategoryEnum catenum);

    @Query("select new com.example.Ecommcerce.models.product.ProductDTO(p.productName, p.manufacturer, p.price, p.quantity) "
            + "from Product p where p.status=:status")
    public List<ProductDTO> getProductsWithStatus(@Param("status") ProductStatus status);

    @Query("select new com.example.Ecommcerce.models.product.ProductDTO(p.productName, p.manufacturer, p.price, p.quantity) "
            + "from Product p where p.seller.sellerId=:id")
    public List<ProductDTO> getProductsOfASeller(@Param("id") Integer id);

}
