package com.example.baolema.DAO;

import com.example.baolema.bean.Activity;

import java.util.List;

public interface ActivityDao {

    List<Activity> getActivitiesByShopId(int shopId);
}
