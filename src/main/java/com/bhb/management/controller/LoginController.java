package com.bhb.management.controller;

import com.bhb.management.mapper.ExcellentEmployeeMapper;
import com.bhb.management.mapper.IUserMapper;
import com.bhb.management.pojo.ExcellentEmployee;
import com.bhb.management.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    IUserMapper userMapper;
    @Autowired
    ExcellentEmployeeMapper excellentEmployeeMapper;

    @GetMapping("/login")
    public String getLogin() {

        return "login";
    }

    @PostMapping("/login")
    public String doLogin(User user, Model model) {
        // 获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        // 封装用户名密码token
        System.out.println("login=======>>>>" + user.getLoginname() + user.getPassword());
        UsernamePasswordToken token =
                new UsernamePasswordToken(user.getLoginname(), user.getPassword());
        // 使用Shiro框架进行登录操作
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            model.addAttribute("msg", "用户名或密码错误");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginname", user.getLoginname());
        List<User> users = userMapper.selectByMap(map);

        for (User us : users) {

            if (subject.hasRole("manager")) {

                model.addAttribute("user", us);

                ExcellentEmployee excellentEmployee = excellentEmployeeMapper.excellentEmployee();
                int excellentid = excellentEmployee.getExcellentid();
                Map<String, Object> excellent = new HashMap<>();
                excellent.put("id", excellentid);
                List<User> users1 = userMapper.selectByMap(excellent);
                for (User user1 : users1) {
                    model.addAttribute("exce",user1);
                }
                return "/manager/index";

            } else if (subject.hasRole("hr")) {

                model.addAttribute("user", us);
                ExcellentEmployee excellentEmployee = excellentEmployeeMapper.excellentEmployee();
                int excellentid = excellentEmployee.getExcellentid();
                Map<String, Object> excellent = new HashMap<>();
                excellent.put("id", excellentid);
                List<User> users1 = userMapper.selectByMap(excellent);
                for (User user1 : users1) {
                    model.addAttribute("exce",user1);
                }
                return "/manager/index";

            } else if (subject.hasRole("employee")) {

                model.addAttribute("user", us);
                return "employee/hello";
            } else {
                System.err.println("---> other");
            }
        }
        return "login";
    }


}
