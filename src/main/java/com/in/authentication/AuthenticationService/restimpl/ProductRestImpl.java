package com.in.authentication.AuthenticationService.restimpl;

import com.in.authentication.AuthenticationService.constants.CafeConstants;
import com.in.authentication.AuthenticationService.rest.ProductRest;
import com.in.authentication.AuthenticationService.service.ProductService;
import com.in.authentication.AuthenticationService.utils.CafeUtilits;
import com.in.authentication.AuthenticationService.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductRestImpl implements ProductRest {

    @Autowired
    ProductService productService;

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try{
            return productService.addProduct(requestMap);
        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getallProduct() {
        try{
            return productService.getallProduct();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            return productService.updateProduct(requestMap);
        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            return productService.deleteProduct(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            return productService.updateStatus(requestMap);
        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try{
            return productService.getByCategory(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getByProductId(Integer id) {
        try{
            return productService.getByProductId(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
