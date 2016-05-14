package com.example.a27043.myapplication.service;

import com.example.a27043.myapplication.entity.User;

/**
 * Created by 27043 on 2016-05-07.
 */
public class UserService {
    public User login(String code, String password){
        if ("test".equals(code) && "test".equals(password)){
            User u = new User();
            u.id = 1;
            u.code = code;
            u.password = password;
            u.name = "朱永望";
            return u;
        }
        return null;
    }
}
