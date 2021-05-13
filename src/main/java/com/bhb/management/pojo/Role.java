package com.bhb.management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("m_role")
public class Role {
    @TableId(type = IdType.AUTO) //指定id类型为自增长
    private int id;
    private String rolename;
}
