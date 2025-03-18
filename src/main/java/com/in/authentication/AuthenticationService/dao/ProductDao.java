package com.in.authentication.AuthenticationService.dao;

import com.in.authentication.AuthenticationService.pojo.Product;
import com.in.authentication.AuthenticationService.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<ProductWrapper> getAllProduct();
}
