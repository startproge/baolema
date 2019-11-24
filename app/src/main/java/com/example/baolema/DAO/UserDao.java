package com.example.baolema.DAO;

import com.example.baolema.bean.User;

public interface UserDao {
    // url/User/getUser?userId=
    User getUser(int userId);

}
