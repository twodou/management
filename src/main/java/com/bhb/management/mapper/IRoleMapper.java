package com.bhb.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhb.management.pojo.Role;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Mapper
public interface IRoleMapper extends BaseMapper<Role> {

    Set<String> queryAllRolenameByLoginname(String loginname);
}