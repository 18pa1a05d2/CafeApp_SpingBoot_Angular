package com.in.authentication.AuthenticationService.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

    public void forgetMail(String to, String subject, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("18pa1a05d2@vishnu.edu.in");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMessage = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(htmlMessage,"text/html");
        emailSender.send(message);
    }
}
