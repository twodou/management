package com.bhb.management.realm;


import com.bhb.management.mapper.IUserMapper;
import com.bhb.management.pojo.User;
import com.bhb.management.service.IPermissionService;
import com.bhb.management.service.IRoleService;
import com.bhb.management.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IUserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取当前用户的用户名
        String loginname = (String) principalCollection.getPrimaryPrincipal();

        // 查询当前用户的权限信息
        Set<String> roles = roleService.queryAllRolenameByLoginname(loginname);
        Set<String> permissions = permissionService.queryAllPermissionnameByLoginname(loginname);
        System.err.println(roles + "+" + permissions);
        // 将查询出的信息封装到 AuthorizationInfo 中
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setStringPermissions(permissions);

        // 查询后封装到指定对象后返回即可。
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 获取用户登录时发送来的用户名
        String loginname = (String) authenticationToken.getPrincipal();
        // 查询到用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginname", loginname);
        List<User> users = userMapper.selectByMap(map);

        for (User user : users) {

            if (user == null) { // 不存在用户名
                System.out.println("不存在用户名!");
                return null; // 返回了null，则后续流程中会抛出一个UnknownAccountException
            } // 将用户信息封装在 AuthenticationInfo 中

            System.err.println(user.toString() + "+" + user.getSalt() + "+" + user.getPassword());
            return new SimpleAuthenticationInfo(
                    user.getLoginname(), // 数据库中登录名
                    user.getPassword(), // 数据库中的密码
                    ByteSource.Util.bytes(user.getSalt()), // 数据库中存储的盐值
                    this.getName()); // realm的标识
        }
        return null;
    }

}
