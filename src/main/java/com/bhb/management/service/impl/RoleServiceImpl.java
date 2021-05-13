package com.bhb.management.service.impl;



import com.bhb.management.mapper.IRoleMapper;
import com.bhb.management.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements IRoleService {
  @Autowired
  private IRoleMapper iRoleMapper;

  @Override
  public Set<String> queryAllRolenameByLoginname(String loginname) {
    return iRoleMapper.queryAllRolenameByLoginname(loginname);
  }
}
