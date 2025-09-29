package com.spring.jwt_auth_prog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.jwt_auth_prog.model.Product;
import com.spring.jwt_auth_prog.repo.ProductRepo;

@Service
public class ProductService {
    private final ProductRepo repo;

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProduct(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return repo.save(product);
    }

}
