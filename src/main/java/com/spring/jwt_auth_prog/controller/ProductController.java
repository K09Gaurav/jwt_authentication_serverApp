package com.spring.jwt_auth_prog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jwt_auth_prog.model.Product;
import com.spring.jwt_auth_prog.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;






@RestController
@RequestMapping("")
public class ProductController {

    private ProductService service;
    public ProductController(ProductService service) {
        this.service = service;
    }


    @RequestMapping("/")
    public String welcome(HttpServletRequest reqqHttpServletRequest){
        String toReturn = "This Page is Welcome Page on Sesion : "+ reqqHttpServletRequest.getSession().getId();
        return toReturn;
    }


    @GetMapping("/api/products")
    public ResponseEntity<List<Product>> getAllProduct(){

        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/api/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id)  {
        Product product =  service.getProduct(id);

        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product saved = service.saveProduct(product);


        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


}
