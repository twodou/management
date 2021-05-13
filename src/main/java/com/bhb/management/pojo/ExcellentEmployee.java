package com.bhb.management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("excellent")
public class ExcellentEmployee {

    @TableId(type = IdType.AUTO)
    private int id;
    private int excellentid;
    private Date excellenttime;

}
