package com.example.baolema.DAO;

import com.example.baolema.bean.User;

import java.util.List;

public interface UserDao {
    List<User> getUserList(int userId);

}
