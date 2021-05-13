package com.bhb.management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhb.management.mapper.DepartmentMapper;
import com.bhb.management.mapper.INoticeMapper;
import com.bhb.management.mapper.IUserMapper;
import com.bhb.management.pojo.Department;
import com.bhb.management.pojo.Notice;
import com.bhb.management.pojo.User;
import com.bhb.management.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/manager/notice")
public class NoticeController {

    @Autowired
    IUserMapper userMapper;

    @Autowired
    IUserService userService;

    @Autowired
    INoticeMapper noticeMapper;

    @GetMapping("/page")
    public String pages(@RequestParam("n") String n, Model model, @RequestParam("loginname") String loginname) {

        int pages = Integer.parseInt(n);
        Map<String, Object> map = new HashMap<>();
        map.put("loginname", loginname);
        List<User> users = userMapper.selectByMap(map);

        for (User user : users) {
            model.addAttribute("user", user);
        }
        Page<Notice> page = new Page<>(pages, 5);

        noticeMapper.selectPage(page, null);
        List<Notice> records = page.getRecords();

        long alln = page.getPages();
        if (records == null) {
            model.addAttribute("nullMsg", "暂无数据");
        } else {
            long size = page.getSize();
            model.addAttribute("size", size);
            model.addAttribute("pages", pages);
            model.addAttribute("alln", alln);
            model.addAttribute("noticeAll", records);
        }

        return "manager/notice";
    }
    @RequestMapping("/queryByIdName")
    public String queryByIdName(Model model,
                                @RequestParam("adminLoginname") String adminLoginname,
                                @RequestParam("username") String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        List<User> users = userMapper.selectList(wrapper);
        if (users.size() == 0) {
            model.addAttribute("nullMsg", "数据库暂无信息或您查询条件里未录入任何信息");
        } else {
            model.addAttribute("userAll", users);
        }
        model.addAttribute("pages", 1);
        model.addAttribute("alln", 1);

        Map<String, Object> map = new HashMap<>();
        map.put("loginname", adminLoginname);
        List<User> userss = userMapper.selectByMap(map);
        for (User user : userss) {
            model.addAttribute("user", user);
        }

        return "manager/notice";
    }
}
