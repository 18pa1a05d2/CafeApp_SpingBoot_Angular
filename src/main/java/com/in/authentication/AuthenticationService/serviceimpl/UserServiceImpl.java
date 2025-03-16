package com.in.authentication.AuthenticationService.serviceimpl;

import com.in.authentication.AuthenticationService.constants.CafeConstants;
import com.in.authentication.AuthenticationService.dao.UserDao;
import com.in.authentication.AuthenticationService.jwt.CustomerUsersDetailsService;
import com.in.authentication.AuthenticationService.jwt.JwtFilter;
import com.in.authentication.AuthenticationService.jwt.JwtUtil;
import com.in.authentication.AuthenticationService.pojo.User;
import com.in.authentication.AuthenticationService.service.UserService;
import com.in.authentication.AuthenticationService.utils.CafeUtilits;
import com.in.authentication.AuthenticationService.utils.EmailUtils;
import com.in.authentication.AuthenticationService.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userdao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    //Signup
    @Override
    public ResponseEntity<String> singUp(Map<String, String> requestMap) {

        try {
            if (validateSignUpMap(requestMap)) {

                User user = userdao.findByEmailID(requestMap.get("email"));
                //log.info("is object null "+ Objects.isNull(user));
                if (Objects.isNull(user)) {
                    userdao.save(getUserFromMap(requestMap));
                    //log.info("returning values to postman");
                    return CafeUtilits.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return CafeUtilits.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
                }
            } else {
               // log.info("inside else block of validation failure");
                return CafeUtilits.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception ex){
            //log.error("erorr message is : "+ ex.getMessage());
            ex.printStackTrace();
        }
        return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private boolean validateSignUpMap(Map<String, String> requestMap){
        return (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password"));
    }

    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
    //login
    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        System.out.println("Inside login");
        try{
            Authentication auth= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if(auth.isAuthenticated()){
                //this is for admin approal verification whether admin approved to acces the page or not
                if(customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
                                    customerUsersDetailsService.getUserDetail().getRole())+ "\"}",
                            HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}", HttpStatus.BAD_REQUEST);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad credentials"+"\"}", HttpStatus.BAD_REQUEST);
    }

    //get all User id with role user when admin is accessing it
    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try{
            if(jwtFilter.isAdmin()){
                System.out.println("Admin logged in");
                return new ResponseEntity<>(userdao.getAllUser(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Update status by an ID
    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
       try{

           //check whether user is Admin
           if(jwtFilter.isAdmin()) {
               //check whether Id exists in Table
               Optional<User> optional = userdao.findById(Integer.parseInt(requestMap.get("id")));
               if(!optional.isEmpty()){
                   //update the user status by ID
                   userdao.UpdateUser(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                   //to send email to all Admins when an user is updated (approved or disabled)
                   //first fetch the list of all admin users in the inner function call below
                   sendMailtoAllAdmin(requestMap.get("status"), optional.get().getEmail(), userdao.getAllAdmin());
                   return CafeUtilits.getResponseEntity("User status updated successfully", HttpStatus.OK);
               }else{
                   CafeUtilits.getResponseEntity("User Id doesn't exists", HttpStatus.OK);
               }
           }else{
               return CafeUtilits.getResponseEntity(CafeConstants.UNAUTHORISED_ACCESS, HttpStatus.UNAUTHORIZED);
           }
       }catch(Exception e){
           e.printStackTrace();
       }
       return CafeUtilits.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailtoAllAdmin(String status, String user, List<String> allAdmin) {
        //as we logged in with one user and to avoid duplicate mail to that user we are excluding current user from list
        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status!=null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved", "USER:- "+user+"\n is approved by \nADMIN:-"+jwtFilter.getCurrentUser() +")",allAdmin);
        }else{
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disabled", "USER:- "+user+"\n is disabled by \nADMIN:-"+jwtFilter.getCurrentUser() +")",allAdmin);
        }
    }
}
