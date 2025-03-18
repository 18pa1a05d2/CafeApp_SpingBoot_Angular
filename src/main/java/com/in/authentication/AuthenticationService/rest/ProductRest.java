package com.in.authentication.AuthenticationService.rest;

import com.in.authentication.AuthenticationService.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/product")
public interface ProductRest {

    @PostMapping(path="/add")
    ResponseEntity<String> addProduct(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path="/get")
    ResponseEntity<List<ProductWrapper>> getallProduct();

    @PutMapping(path="/update")
    ResponseEntity<String> updateProduct(@RequestBody(required = true) Map<String, String> requestMap);


}
