package com.bhb.management.service.impl;


import com.bhb.management.mapper.IUserMapper;
import com.bhb.management.pojo.User;
import com.bhb.management.service.IUserService;
import com.bhb.management.untils.MailUtil;
import lombok.SneakyThrows;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServlceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;
    @Autowired
    JavaMailSenderImpl mailSender;

    @Override
    public int changerImgByLoginname(String path, String loginname) {
        return userMapper.changerImgByLoginname(path, loginname);
    }

    @Override
    public int setUser_create(User user) {

        String data = "2000-01-01 08:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date eDate = sdf.parse(data);

            user.setLogintime(new Date());
            user.setBirth(eDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 盐值
        String salt = UUID.randomUUID().toString(); // 随机生成盐值
        // 加密：使用sha256算法，添加盐值，迭代10次加密，加密后进行base64编码
        int random = (int) ((Math.random() * 9 + 1) * 100000);
        String password = String.valueOf(random);

        //异步发送邮件
        sendmail(user.getLoginname(), user.getEmail(), password);

        String pwd = new Sha256Hash(password, salt, 10).toBase64();
        // 更新数据
        System.out.println(salt + ":" + pwd);
        user.setPassword(pwd);
        if (user.getDepid() == 3) {
            user.setUserroleid(3);//设置为hr
        } else {
            user.setUserroleid(2);//设置为employee
        }

        user.setStatus(1);
        user.setSalt(salt);
        user.setPhone("暂无");
        user.setExcellentnumber(0);
        user.setImg("/static/assets/img/01.jpg");
        user.setSex(0);
        user.setSfz("123456789987654321");
        user.setQq("11111");
        user.setWechar("11111");
        user.setAge(20);

        System.err.println(user.toString());

        int insert = userMapper.insert(user);

        if (insert == 1) {
            String loginname = user.getLoginname();
            Map<String, Object> map = new HashMap<>();
            map.put("loginname", loginname);
            List<User> users = userMapper.selectByMap(map);
            for (User use : users) {
                int id = use.getId();
                if (use.getUserroleid() == 3) {
                    userMapper.inRolePermission(id, 1);
                    userMapper.inUserRole(id, 1);
                } else {
                    userMapper.inRolePermission(id, 2);
                    userMapper.inUserRole(id, 2);
                }

            }
        } else {
            return 0;
        }
        return 1;
    }

    @Async
    public void sendmail(String loginname, String email, String password) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("欢迎你加入我们!");
        mailMessage.setText("<p style='color:red'>您的个人密码是: " + password + "</p>" +
                "您的个人登陆名是: " + loginname);
        mailMessage.setTo(email);
        mailMessage.setFrom("1742273735@qq.com");
        mailSender.send(mailMessage);

    }

}
