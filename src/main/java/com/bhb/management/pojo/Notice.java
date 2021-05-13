package com.bhb.management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("notice")
public class Notice {

    @TableId(type = IdType.AUTO) //指定id类型为自增长
    private int id;
    private String title;
    private String sendname;
    private Date sendtime;
    private String content;

}
