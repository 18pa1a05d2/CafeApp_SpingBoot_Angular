package com.in.authentication.AuthenticationService.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtilits {

    private CafeUtilits(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpstatus){
        //System.out.println("Returning error response: {}", responseMessage);
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpstatus);
    }
}
