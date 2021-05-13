package com.bhb.management.controller;

import com.bhb.management.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    IUserMapper userMapper;

    @GetMapping("/index")
    public String gotoEmployeeIndex() {

        return "employee/hello";
    }

}
