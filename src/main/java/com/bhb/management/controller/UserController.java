package com.bhb.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhb.management.mapper.DepartmentMapper;
import com.bhb.management.mapper.ExcellentEmployeeMapper;
import com.bhb.management.mapper.IUserMapper;
import com.bhb.management.pojo.Department;
import com.bhb.management.pojo.ExcellentEmployee;
import com.bhb.management.pojo.User;
import com.bhb.management.service.IUserService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserMapper userMapper;

    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    IUserService userService;

    @Autowired
    ExcellentEmployeeMapper excellentEmployeeMapper;

    @GetMapping("/page")
    public String pages(@RequestParam("n") String n, Model model, @RequestParam("loginname") String loginname) {
        int pages = Integer.parseInt(n);
        Map<String, Object> map = new HashMap<>();
        map.put("loginname", loginname);
        List<User> users = userMapper.selectByMap(map);

        for (User user : users) {
            model.addAttribute("user", user);
        }
        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);
        Page<User> page = new Page<>(pages, 5);

        userMapper.selectPage(page, null);
        List<User> records = page.getRecords();
        long alln = page.getPages();
        if (records == null) {
            model.addAttribute("nullMsg", "暂无数据");
        } else {
            long size = page.getSize();
            model.addAttribute("size", size);
            model.addAttribute("pages", pages);
            model.addAttribute("alln", alln);
            model.addAttribute("userAll", records);
        }

        return "manager/user";
    }

    @GetMapping("/create/{loginname}")
    public String createUser(@PathVariable String loginname, Model model) {
        Map<String, Object> map = new HashMap<>();
        map.put("loginname", loginname);
        List<User> users = userMapper.selectByMap(map);
        for (User user : users) {
            model.addAttribute("user", user);
        }
        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);

        return "manager/user_create";
    }

    @PostMapping("/user_create")
    public String userCreate(User createUser, @RequestParam("adminLoginname") String adminLoginname, Model model) {
        System.out.println(createUser.toString());
        //检查loginname是否重复
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("loginname", createUser.getLoginname());
        List<User> users1 = userMapper.selectByMap(hashMap);
        System.err.println(users1.size());
        if (users1.size() != 0) {
            model.addAttribute("msg", "登陆名重复!请重新选择!");
            Map<String, Object> map = new HashMap<>();
            map.put("loginname", adminLoginname);
            List<User> users = userMapper.selectByMap(map);
            for (User user : users) {
                model.addAttribute("user", user);
            }
            List<Department> departments = departmentMapper.queryAll();
            model.addAttribute("departments", departments);

            return "manager/user_create";
        }
        //set user信息
        userService.setUser_create(createUser);


        Map<String, Object> map = new HashMap<>();
        map.put("loginname", adminLoginname);
        List<User> users = userMapper.selectByMap(map);
        for (User user : users) {
            model.addAttribute("user", user);
        }
        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);

        return "manager/user_create";
    }

    @RequestMapping("/queryByIdName")
    public String queryByIdName(Model model,
                                @RequestParam("adminLoginname") String adminLoginname,
                                @RequestParam("departmentid") int departmentid,
                                @RequestParam("username") String username) {
        System.err.println(departmentid + "=====" + username);
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if (departmentid == 0) {
            wrapper.like("username", username);
        }
        if (Objects.equals(username, "")) {
            wrapper.eq("depid", departmentid);
        }

        if (departmentid != 0 && username != null) {
            wrapper.eq("depid", departmentid).like("username", username);
        }
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

        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);

        return "manager/user";
    }

    @RequestMapping("/edit")
    public String userInfo(@RequestParam("loginname") String loginname,
                           Model model,
                           @RequestParam("id") int id) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("loginname", loginname);
        List<User> users = userMapper.selectByMap(map);
        for (User user : users) {
            model.addAttribute("user", user);
        }
        HashMap<String, Object> edit = new HashMap<>();
        edit.put("id", id);
        List<User> edit_users = userMapper.selectByMap(edit);
        for (User user : edit_users) {
            model.addAttribute("edit_user", user);
        }
        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);
        return "manager/user_edit";
    }

    @PostMapping("/edit_updateUser")
    public String updateUser(User user, Model model,
                             @RequestParam("birth_a") String birth_a,
                             @RequestParam("adminLoginname") String adminLoginname) {
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
        Map<String, Object> edit = new HashMap<>();
        edit.put("loginname", loginname);
        List<User> users = userMapper.selectByMap(edit);
        for (User use : users) {
            model.addAttribute("edit_user", use);

        }

        Map<String, Object> map = new HashMap<>();
        map.put("loginname", adminLoginname);
        List<User> userss = userMapper.selectByMap(map);
        for (User use : userss) {
            model.addAttribute("user", use);

        }

        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);
        return "manager/user_edit";
    }

    @GetMapping("/details")
    public String details(Model model,
                          @RequestParam("id") String id,
                          @RequestParam("loginname") String loginname) {

        Map<String, Object> edit = new HashMap<>();
        edit.put("id", id);
        List<User> users = userMapper.selectByMap(edit);
        for (User use : users) {
            model.addAttribute("edit_user", use);

        }

        Map<String, Object> map = new HashMap<>();
        map.put("loginname", loginname);
        List<User> userss = userMapper.selectByMap(map);
        for (User use : userss) {
            model.addAttribute("user", use);

        }

        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);
        return "manager/userinfo_detail";
    }

    @RequestMapping("/delete")
    public String deleteUser(Model model,
                             @RequestParam("id") String id,
                             @RequestParam("loginname") String loginname) {
        int i = userMapper.deleteById(id);
        if (i == 1) {
            model.addAttribute("nullMsg", "删除成功");
        } else {
            model.addAttribute("nullMsg", "删除失败或您正在刷新删除后的页面重复提交数据");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("loginname", loginname);
        List<User> users = userMapper.selectByMap(map);
        for (User user : users) {
            model.addAttribute("user", user);
        }
        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);
        return "manager/user";
    }

    @RequestMapping("/updatepwd")
    public String updatePassword(Model model,
                                 @RequestParam("id") int id,
                                 @RequestParam("loginname") String loginname) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<User> users = userMapper.selectByMap(map);
        for (User user : users) {
            // 盐值
            String salt = UUID.randomUUID().toString(); // 随机生成盐值
            // 加密：使用sha256算法，添加盐值，迭代10次加密
            int random = (int) ((Math.random() * 9 + 1) * 100000);
            String password = String.valueOf(random);

            //异步发送邮件
            userService.sendmail(user.getLoginname(), user.getEmail(), password);

            String pwd = new Sha256Hash(password, salt, 10).toBase64();
            // 更新数据
            user.setPassword(pwd);
            user.setSalt(salt);
            user.setId(id);
            int i = userMapper.updateById(user);
            if (i == 1) {
                model.addAttribute("nullMsg", "修改成功");
            } else {
                model.addAttribute("nullMsg", "修改失败或您正在刷新删除后的页面重复提交数据");
            }

        }

        Map<String, Object> maps = new HashMap<>();
        maps.put("loginname", loginname);
        List<User> userss = userMapper.selectByMap(maps);
        for (User user : userss) {
            model.addAttribute("user", user);
        }
        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);
        return "manager/user";
    }

    @RequestMapping("/setExcellent")
    public String setExcellent(Model model,
                               @RequestParam("id") int id,
                               @RequestParam("loginname") String loginname) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<User> users = userMapper.selectByMap(map);
        for (User user : users) {
            int excellentnumber = user.getExcellentnumber();
            excellentnumber = excellentnumber + 1;
            user.setExcellentnumber(excellentnumber);
            userMapper.updateById(user);
            ExcellentEmployee excellentEmployee = new ExcellentEmployee();
            excellentEmployee.setExcellenttime(new Date());
            excellentEmployee.setExcellentid(user.getId());
            excellentEmployeeMapper.insert(excellentEmployee);

        }

        Map<String, Object> maps = new HashMap<>();
        maps.put("loginname", loginname);
        List<User> userss = userMapper.selectByMap(maps);
        for (User user : userss) {
            model.addAttribute("user", user);
        }
        List<Department> departments = departmentMapper.queryAll();
        model.addAttribute("departments", departments);

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
}
