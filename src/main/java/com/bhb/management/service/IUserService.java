package com.bhb.management.service;


import com.bhb.management.pojo.User;

public interface IUserService {

    int changerImgByLoginname(String path, String loginname);
    int setUser_create(User user);

    void sendmail(String password, String email, String loginname);
}
