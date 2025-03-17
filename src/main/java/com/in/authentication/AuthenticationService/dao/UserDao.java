package com.in.authentication.AuthenticationService.dao;

import com.in.authentication.AuthenticationService.pojo.User;
import com.in.authentication.AuthenticationService.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

        User findByEmailID(@Param("email") String email);

        List<UserWrapper> getAllUser();

        @Transactional
        @Modifying
        Integer UpdateUser(@Param("status") String status, @Param("id") Integer id);

        List<String> getAllAdmin();

//        @Transactional
//        @Modifying
//        void updatePassword(@Param("email") String email, @Param("password") String password);
}
