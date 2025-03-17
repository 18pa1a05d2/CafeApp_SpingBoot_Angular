package com.in.authentication.AuthenticationService.restimpl;

import com.in.authentication.AuthenticationService.constants.CafeConstants;
import com.in.authentication.AuthenticationService.pojo.Category;
import com.in.authentication.AuthenticationService.rest.CategoryRest;
import com.in.authentication.AuthenticationService.service.CatergoryService;
import com.in.authentication.AuthenticationService.utils.CafeUtilits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestImpl implements CategoryRest {
    @Autowired
    CatergoryService categoryService;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            return categoryService.addNewCategory(requestMap);
        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            return categoryService.getAllCategory(filterValue);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}