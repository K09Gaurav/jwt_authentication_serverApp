package com.spring.jwt_auth_prog;

import java.net.URI;
import java.util.List;

import org.springframework.boot.autoconfigure.jersey.JerseyProperties.Servlet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("")
public class controller {

    private ProductService service;
    public controller(ProductService service) {
        this.service = service;
    }


    @RequestMapping("/")
    public String welcome(HttpServletRequest reqqHttpServletRequest){
        String toReturn = "This Page is Welcome Page on Sesion : "+ reqqHttpServletRequest.getSession().getId();
        return toReturn;
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct(){

        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id)  {
        Product product =  service.getProduct(id);

        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product saved = service.saveProduct(product);


        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


}
