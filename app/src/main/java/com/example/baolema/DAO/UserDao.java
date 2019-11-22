package com.example.baolema.DAO;

import com.example.baolema.bean.User;

public interface UserDao {
    // url?/type=android&req=user
    User getUser(int userId);

}
