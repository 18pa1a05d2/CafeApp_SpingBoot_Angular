package com.in.authentication.AuthenticationService.rest;

import com.in.authentication.AuthenticationService.pojo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping(path="/category")
public interface CategoryRest {

    @PostMapping(path="/add")
    ResponseEntity<String> addNewCategory(@RequestBody(required=true) Map<String, String> requestMap);

    //filterValue is used beacuse admin should be able to see all categories, and for role no access to that product
    @GetMapping(path="/get")
    ResponseEntity<List<Category>> getAllCategory(@RequestParam(required=false) String filterValue);

    @PutMapping(path="/update")
    ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String, String> requestMap);

}
