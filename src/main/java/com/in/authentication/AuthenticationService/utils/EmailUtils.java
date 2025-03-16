package com.in.authentication.AuthenticationService.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> list){
        SimpleMailMessage message= new SimpleMailMessage();
        message.setFrom("18pa1a05d2@vishnu.edu.in");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(list!=null && list.size()>0){
            message.setCc(getccArray(list));
        }
        emailSender.send(message);
    }

    private String[] getccArray(List<String> ccList){
        String[] cc= new String[ccList.size()];
        for(int i=0; i<ccList.size();i++){
            cc[i] = ccList.get(i);
        }
        return cc;
    }
}
