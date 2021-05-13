package com.bhb.management;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhb.management.controller.UserController;
import com.bhb.management.mapper.ExcellentEmployeeMapper;
import com.bhb.management.mapper.IUserMapper;
import com.bhb.management.pojo.ExcellentEmployee;
import com.bhb.management.pojo.User;
import com.bhb.management.service.IPermissionService;
import com.bhb.management.service.IRoleService;
import com.bhb.management.service.impl.UserServlceImpl;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class ManagementApplicationTests {

    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IRoleService roleService;
    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    ExcellentEmployeeMapper excellentEmployeeMapper;
    @Test
    protected void doGetAuthorizationInfo() {
        // 获取当前用户的用户名
        String loginname = "zhangsan";
        // 查询当前用户的权限信息
        Set<String> roles = roleService.queryAllRolenameByLoginname(loginname);
        Set<String> permissions = permissionService.queryAllPermissionnameByLoginname(loginname);
        System.err.println(roles + "+" + permissions);

    }

    @Autowired
    HttpSession session;
    @Test
    void contextLoads() {
        session.setAttribute("hello","hello");
        System.out.println(session.getAttribute("hello"));
    }

    @Test
    void contextLoads2( ) {
        Page<User> page = new Page<>(2, 2);
        userMapper.selectPage(page, null);

        Long maxLimit = page.getMaxLimit();
        System.out.println(maxLimit);
        long total = page.getTotal();
        System.out.println(total);
        long pages = page.getPages();
        System.out.println(pages);
        List<User> records = page.getRecords();
        for (User user :
                records) {
            System.out.println(user.getBirth());
            System.out.println(user.getLogintime());
        }
    }

    @Test
    void pwd(){
        DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strBeginDate = dateTimeformat.format(new Date());
        System.out.println(strBeginDate);
    }

    @Test
    void tiaojian(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.eq("depid",3).like("username","王");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);

    }

    @Test
    void edit(){
        ExcellentEmployee excellentEmployee = excellentEmployeeMapper.excellentEmployee();
        System.out.println(excellentEmployee.toString());

    }
}
