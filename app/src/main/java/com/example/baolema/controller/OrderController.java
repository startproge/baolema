package com.example.baolema.controller;

import android.graphics.Typeface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.baolema.DAO.OrderDao;
import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderMain;
import com.example.baolema.bean.Orders;
import com.example.baolema.util.httpUtil;

import java.net.URL;
import java.util.List;

public class OrderController implements OrderDao {


    @Override
    public List<Orders> getOrderList(int userId) {
        String path="http://47.98.229.17:8002/blm/Order/getOrderList?userId="+userId;
        String result=httpUtil.getHttpInterface(path);
        List<Orders> lst= JSON.parseArray(result,Orders.class);
        return lst;
    }

    @Override
    public List<OrderInf> getOrderInfList(int orderId) {
        String path="http://47.98.229.17:8002/blm/OrderInf/getOrderInfList?orderId="+orderId;
        String result=httpUtil.getHttpInterface(path);
        List<OrderInf> lst= JSON.parseArray(result,OrderInf.class);
        return lst;
    }
}
