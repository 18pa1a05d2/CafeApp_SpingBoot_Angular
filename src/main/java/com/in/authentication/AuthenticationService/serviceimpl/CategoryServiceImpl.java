package com.in.authentication.AuthenticationService.serviceimpl;

import com.in.authentication.AuthenticationService.constants.CafeConstants;
import com.in.authentication.AuthenticationService.dao.CatergoryDao;
import com.in.authentication.AuthenticationService.jwt.JwtFilter;
import com.in.authentication.AuthenticationService.pojo.Category;
import com.in.authentication.AuthenticationService.service.CatergoryService;
import com.in.authentication.AuthenticationService.utils.CafeUtilits;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CatergoryService {

    @Autowired
    CatergoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    //to add new category
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            //only admins can add category so check whether user is admin or not
            if(jwtFilter.isAdmin()){
                if(validateCategory(requestMap, false)){
                   categoryDao.save(getCategoryFromMap(requestMap, false));
                   return CafeUtilits.getResponseEntity("Category added succefully", HttpStatus.OK);
                }
            }else{
                CafeUtilits.getResponseEntity(CafeConstants.UNAUTHORISED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateCategory(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
           if(requestMap.containsKey("id") && validateId){
               return true;
           }else if(!validateId){
               return true;
           }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String, String> request, Boolean isAdd){
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(request.get("id")));
        }
        category.setName(request.get("name"));
        return category;
    }

    //to get list of all categories and filterValue is used to get list only for admins
    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            if(null!=filterValue && !filterValue.isEmpty()  && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //to add more categories
    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                //validation if empty data exists then rejected
                if(validateCategory(requestMap, true)){

                    //Id validation what if we pass wrong ID
                   Optional optional =  categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                   if(!optional.isEmpty()){

                        categoryDao.save(getCategoryFromMap(requestMap, true));
                                return CafeUtilits.getResponseEntity("Category update successfully",HttpStatus.OK);
                   }else{
                       return CafeUtilits.getResponseEntity("Category Id does not exist ", HttpStatus.OK);
                   }
                }
                return CafeUtilits.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else{
                return CafeUtilits.getResponseEntity(CafeConstants.UNAUTHORISED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
