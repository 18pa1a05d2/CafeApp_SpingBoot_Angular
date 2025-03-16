package com.in.authentication.AuthenticationService.jwt;

import com.in.authentication.AuthenticationService.dao.UserDao;
import com.in.authentication.AuthenticationService.utils.CafeUtilits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service

public class CustomerUsersDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    private com.in.authentication.AuthenticationService.pojo.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        userDetail = userDao.findByEmailID(username);
        if(!Objects.isNull(userDetail)){
            return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("user Not found Exception");
        }
    }

    public com.in.authentication.AuthenticationService.pojo.User getUserDetail(){
        return userDetail;
    }
}
