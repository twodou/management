package com.bhb.management.service;

import java.util.Set;

public interface IPermissionService {
    Set<String> queryAllPermissionnameByLoginname(String loginname);
}
