package com.bhb.management.service;

import java.util.Set;

public interface IRoleService {
    // 根据登录名查询其对应的所有角色名，Shiro框架在处理的时候传递的都是角色名，所以这里直接返回字符串集合
    Set<String> queryAllRolenameByLoginname(String loginname);
}
