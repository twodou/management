package com.bhb.management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("m_user")
public class User {

    @TableId(type = IdType.AUTO) //指定id类型为自增长
    private int id;

    private String username;
    private int userroleid;
    private String loginname;
    private String password;
    private int status;
    private String salt;
    private String email;
    private String phone;
    private int excellentnumber;
    private String img;
    private int sex;
    private String sfz;
    private String qq;
    private String wechar;
    private int depid;
    private Date birth;
    private int age;
    private Date logintime;

}
