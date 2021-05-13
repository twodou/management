package com.bhb.management.controller;

import com.bhb.management.mapper.DepartmentMapper;
import com.bhb.management.mapper.IUserMapper;
import com.bhb.management.pojo.Department;
import com.bhb.management.pojo.User;
import com.bhb.management.service.IUserService;
import groovy.grape.GrapeIvy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {


    @Autowired
    IUserService userService;

    @Autowired
    IUserMapper userMapper;

    @Autowired
    DepartmentMapper departmentMapper;
     
    @RequestMapping("/changerImgByid")
    public String changerImgByLoginname(@RequestParam("id") Integer id,
                                        @RequestParam("loginname") String loginname,
                                        Model model) {

        String img = "/static/assets/img/0" + String.valueOf(id) + ".jpg";
        System.out.println(img);
        int i = userService.changerImgByLoginname(img, loginname);
        System.out.println(i);
        Map<String, Object> map = new HashMap<>();
        map.put("loginname", loginname);
        List<User> users = userMapper.selectByMap(map);
        for (User user : users) {
            model.addAttribute("user", user);

        }
        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);
        return "manager/userinfo";
    }


    @PostMapping("/updateUser")
    public String updateUser(User user, Model model, String birth_a) {
        String data = birth_a + " 08:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date eDate = sdf.parse(data);
            user.setBirth(eDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        userMapper.updateById(user);

        String loginname = user.getLoginname();
        Map<String, Object> map = new HashMap<>();
        map.put("loginname", loginname);
        List<User> users = userMapper.selectByMap(map);
        for (User use : users) {
            model.addAttribute("user", use);

        }
        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);
        return "manager/userinfo";
    }
}
