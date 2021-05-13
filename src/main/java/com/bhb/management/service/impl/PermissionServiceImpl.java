package com.bhb.management.service.impl;


import com.bhb.management.mapper.IPermissionMapper;
import com.bhb.management.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private IPermissionMapper iPermissionMapper;

    @Override
    public Set<String> queryAllPermissionnameByLoginname(String loginname) {
        return iPermissionMapper.queryAllPermissionnameByLoginname(loginname);
    }
}
