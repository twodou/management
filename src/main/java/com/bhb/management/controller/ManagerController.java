package com.bhb.management.controller;

import com.bhb.management.mapper.DepartmentMapper;
import com.bhb.management.mapper.ExcellentEmployeeMapper;
import com.bhb.management.mapper.IUserMapper;
import com.bhb.management.pojo.Department;
import com.bhb.management.pojo.ExcellentEmployee;
import com.bhb.management.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    IUserMapper userMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    ExcellentEmployeeMapper excellentEmployeeMapper;
    @RequestMapping("/index/{loginname}")
    public String getIndex(Model model, @PathVariable String loginname) {

        Map<String, Object> map = new HashMap<>();
        map.put("loginname", loginname);
        List<User> users = userMapper.selectByMap(map);
        for (User user : users) {
            model.addAttribute("user", user);

        }
        ExcellentEmployee excellentEmployee = excellentEmployeeMapper.excellentEmployee();
        int excellentid = excellentEmployee.getExcellentid();
        Map<String, Object> excellent = new HashMap<>();
        excellent.put("id", excellentid);
        List<User> users1 = userMapper.selectByMap(excellent);
        for (User user1 : users1) {
            model.addAttribute("exce",user1);
        }

        return "manager/index";
    }

    @RequestMapping("/userinfo/{loginname}")
    public String userInfo(@PathVariable String loginname, Model model) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("loginname", loginname);
        List<User> users = userMapper.selectByMap(map);
        for (User user : users) {
            model.addAttribute("user", user);

        }
        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);
        return "manager/userinfo";
    }

}
