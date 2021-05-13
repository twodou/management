package com.bhb.management.untils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Async
      public  void  sendmail(String loginname, String email, String password) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("欢迎你加入我们!");
        mailMessage.setText("<p style='color:red'>您的个人密码是: " + password + "</p>" +
                "您的个人登陆名是: " + loginname);
        mailMessage.setTo(email);
        mailMessage.setFrom("1742273735@qq.com");
        mailSender.send(mailMessage);

    }
}
