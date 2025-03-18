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

    @PostMapping("/delete/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id);

    @PostMapping(path="/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody(required= true) Map<String, String> requestMap);

    //to get the data in list<product> when I pass category Id
    @GetMapping(path="/getByCategory/{id}")
    ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Integer id);

    @GetMapping("/getById/{id}")
    ResponseEntity<ProductWrapper> getByProductId(@PathVariable Integer id);


}
