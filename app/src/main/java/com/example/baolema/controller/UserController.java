package com.example.baolema.controller;

import com.alibaba.fastjson.JSON;
import com.example.baolema.DAO.UserDao;
import com.example.baolema.bean.User;
import com.example.baolema.util.httpUtil;

public class UserController implements UserDao {
    @Override
    public User getUser(int userId) {
        String path="http://47.98.229.17:8002/blm//User/getUser?userId="+userId;
        String result= httpUtil.getHttpInterface(path);
        User user= JSON.parseObject(result,User.class);
        return user;
    }
}
