package com.example.baolema.controller;

import com.alibaba.fastjson.JSON;
import com.example.baolema.DAO.ActivityDao;
import com.example.baolema.bean.Activity;
import com.example.baolema.bean.Recipe;
import com.example.baolema.util.httpUtil;

import java.util.List;

public class ActivityController implements ActivityDao {

    @Override
    public List<Activity> getActivitiesByShopId(int shopId) {
        String path="http://ylnzk.cn:8002/blm/Activity/getActivitiesByShopId?shopId="+shopId;
        String result= httpUtil.getHttpInterface(path);
        List<Activity> response=JSON.parseArray(result,Activity.class);
        return  response;
    }
}
