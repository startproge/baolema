package com.example.baolema.controller;

import com.example.baolema.DAO.OrderDao;
import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderSum;

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
