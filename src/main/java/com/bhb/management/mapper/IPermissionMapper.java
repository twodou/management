package com.bhb.management.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhb.management.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Mapper
public interface IPermissionMapper extends BaseMapper<Permission> {

    Set<String> queryAllPermissionnameByLoginname(String loginname);
}