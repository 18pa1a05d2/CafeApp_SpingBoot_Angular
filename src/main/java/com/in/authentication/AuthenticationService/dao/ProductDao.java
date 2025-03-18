package com.in.authentication.AuthenticationService.dao;

import com.in.authentication.AuthenticationService.pojo.Product;
import com.in.authentication.AuthenticationService.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<ProductWrapper> getAllProduct();

    @Modifying
    @Transactional
    Integer updateProductStatus(@Param("status") String status, @Param("id") Integer id);

    List<ProductWrapper> getProductByCategory(@Param("id") Integer id);

    ProductWrapper getByProductId(@Param("id") Integer id);
}
