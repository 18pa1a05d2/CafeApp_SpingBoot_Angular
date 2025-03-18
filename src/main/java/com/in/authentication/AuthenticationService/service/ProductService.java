package com.in.authentication.AuthenticationService.service;

import com.in.authentication.AuthenticationService.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<String> addProduct(Map<String, String> requestMap);

    ResponseEntity<List<ProductWrapper>> getallProduct();

    ResponseEntity<String> updateProduct(Map<String, String> requestMap);
}
