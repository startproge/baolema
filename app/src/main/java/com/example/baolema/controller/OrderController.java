package com.example.baolema.controller;

import com.alibaba.fastjson.JSON;
import com.example.baolema.DAO.OrderDao;
import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderSum;
import com.example.baolema.util.httpUtil;

import java.util.List;

public class OrderController implements OrderDao {

    @Override
    public List<Integer> getOrderSumIdList(int userId) {
        return null;
    }

    @Override
    public OrderSum getOrderSumById(int orderId) {
        return null;
    }

    @Override
    public List<OrderInf> getOrderInformationList(int orderId) {
        return null;
    }
}
