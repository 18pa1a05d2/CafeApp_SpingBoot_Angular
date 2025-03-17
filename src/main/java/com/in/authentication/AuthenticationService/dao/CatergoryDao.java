package com.in.authentication.AuthenticationService.dao;

import com.in.authentication.AuthenticationService.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatergoryDao extends JpaRepository<Category, Integer> {

    List<Category> getAllCategory();
}
