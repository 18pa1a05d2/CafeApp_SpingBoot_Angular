package com.in.authentication.AuthenticationService.serviceimpl;

import com.in.authentication.AuthenticationService.constants.CafeConstants;
import com.in.authentication.AuthenticationService.dao.ProductDao;
import com.in.authentication.AuthenticationService.jwt.JwtFilter;
import com.in.authentication.AuthenticationService.pojo.Category;
import com.in.authentication.AuthenticationService.pojo.Product;
import com.in.authentication.AuthenticationService.service.ProductService;
import com.in.authentication.AuthenticationService.utils.CafeUtilits;
import com.in.authentication.AuthenticationService.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    ProductDao productDao;

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateProductMap(requestMap, false)){
                    productDao.save(getProductFromMap(requestMap, false));
                    return CafeUtilits.getResponseEntity("Product added successfully", HttpStatus.OK);
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

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {

        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("catergoryId")));
        Product product= new Product();
        //If category exists then add Id by its product Id
        if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        }else{
            product.setStatus("true");
        }
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        product.setCategory(category);

        return product;
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateID) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateID){
                return true;
            }else if(!validateID){
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getallProduct() {
        try{
            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateProductMap(requestMap, true)){
                   Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                   if(!optional.isEmpty()){
                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(optional.get().getStatus());
                        productDao.save(product);
                        return CafeUtilits.getResponseEntity("Product Updated successfully" , HttpStatus.OK);
                   }
                   return CafeUtilits.getResponseEntity("Product Id does not exist", HttpStatus.OK);
                }else{
                    return CafeUtilits.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            }
            else{
                return CafeUtilits.getResponseEntity(CafeConstants.UNAUTHORISED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            if(jwtFilter.isAdmin()){
                Optional optional =  productDao.findById(id);
                if(!optional.isEmpty()){
                    productDao.deleteById(id);
                    return CafeUtilits.getResponseEntity("Product Deleted Successfully", HttpStatus.OK);
                }
                return CafeUtilits.getResponseEntity("Product Id does not exists", HttpStatus.OK);
            }else{
                return CafeUtilits.getResponseEntity(CafeConstants.UNAUTHORISED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
               Optional optional =  productDao.findById(Integer.parseInt(requestMap.get("id")));
               if(!optional.isEmpty()){
                   productDao.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                   return CafeUtilits.getResponseEntity("Product updated successfully ", HttpStatus.OK);
               }
               return CafeUtilits.getResponseEntity("Product Id does not exist", HttpStatus.OK);
            }else{
                return CafeUtilits.getResponseEntity(CafeConstants.UNAUTHORISED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try{

            return new ResponseEntity<>(productDao.getProductByCategory(id), HttpStatus.OK);

        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getByProductId(Integer id) {
       try{
            return new ResponseEntity<>(productDao.getByProductId(id), HttpStatus.OK);
       }catch(Exception e){
            e.printStackTrace();
       }
       return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
