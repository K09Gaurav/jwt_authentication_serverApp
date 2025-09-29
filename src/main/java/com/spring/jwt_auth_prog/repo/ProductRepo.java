package com.spring.jwt_auth_prog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.jwt_auth_prog.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>{

}
