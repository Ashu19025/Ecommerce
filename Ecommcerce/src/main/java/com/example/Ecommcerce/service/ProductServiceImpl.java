package com.example.Ecommcerce.service;

import com.example.Ecommcerce.exception.CategoryNotFoundException;
import com.example.Ecommcerce.exception.ProductNotFoundException;
import com.example.Ecommcerce.exception.SellerException;
import com.example.Ecommcerce.models.CategoryEnum;
import com.example.Ecommcerce.models.product.Product;
import com.example.Ecommcerce.models.product.ProductDTO;
import com.example.Ecommcerce.models.product.ProductStatus;
import com.example.Ecommcerce.models.seller.Seller;
import com.example.Ecommcerce.repository.ProductDeo;
import com.example.Ecommcerce.repository.SellerDeo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDeo productDeo;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerDeo sellerDeo;

    @Override
    @Transactional
    public Product addProductToCatalog(String token, Product product) throws SellerException {
        Product prod = null;
        Seller seller1 = sellerService.getCurrentlyLoggedInSeller(token);
        product.setSeller(seller1);

        Seller existingSeller = sellerService.getSellerByMobile(product.getSeller().getMobile(), token);
        Optional<Seller> opt = sellerDeo.findById(existingSeller.getSellerId());

        if (opt.isPresent()) {
            Seller seller = opt.get();

            product.setSeller(seller);

            prod = productDeo.save(product);

            if (seller.getProduct() == null) {
                seller.setProduct(new ArrayList<>());
            }
            seller.getProduct().add(product);
            sellerDeo.save(seller);

        } else {
            prod = productDeo.save(product);
        }

        return prod;
    }

    @Override
    public Product getProductFromCatalogById(Integer id) throws ProductNotFoundException {
        Optional<Product> opt = productDeo.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new ProductNotFoundException("Product not found with given id");
        }
    }

    @Override
    public String deleteProductFromCatalog(Integer id) throws ProductNotFoundException {
        Optional<Product> opt = productDeo.findById(id);

        if (opt.isPresent()) {
            Product prod = opt.get();
            productDeo.delete(prod);
            return "Product deleted from catalog";
        } else {
            throw new ProductNotFoundException("Product not found with given id");
        }
    }

    @Override
    public Product updateProductIncatalog(Product prod) throws ProductNotFoundException {
        Optional<Product> opt = productDeo.findById(prod.getProductId());

        if (opt.isPresent()) {
            return productDeo.save(prod);
        } else {
            throw new ProductNotFoundException("Product not found with given id");
        }
    }

    @Override
    public List<Product> getAllProductsIncatalog() {
        List<Product> list = productDeo.findAll();

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new ProductNotFoundException("No products in catalog");
        }
    }

    @Override
    public List<ProductDTO> getProductsOfCategory(CategoryEnum catenum) {
        List<ProductDTO> list = productDeo.getAllProductsInACategory(catenum);
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new CategoryNotFoundException("No products found with category:" + catenum);
        }
    }

    @Override
    public List<ProductDTO> getProductsOfStatus(ProductStatus status) {
        List<ProductDTO> list = productDeo.getProductsWithStatus(status);

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new ProductNotFoundException("No products found with given status:" + status);
        }
    }

    @Override
    public Product updateProductQuantityWithId(Integer id, ProductDTO prodDto) {
        Product prod = null;
        Optional<Product> opt = productDeo.findById(id);

        if (opt.isPresent()) {
            prod = opt.get();
            prod.setQuantity(prod.getQuantity() + prodDto.getQuantity());
            if (prod.getQuantity() > 0) {
                prod.setStatus(ProductStatus.AVAILABLE);
            }
            productDeo.save(prod);

        } else {
            throw new ProductNotFoundException("No product found with this Id");
        }

        return prod;
    }

    @Override
    public List<ProductDTO> getAllProductsOfSeller(Integer id) {
        List<ProductDTO> list = productDeo.getProductsOfASeller(id);

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new ProductNotFoundException("No products with SellerId: " + id);
        }
    }
}
