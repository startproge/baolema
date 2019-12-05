package com.example.baolema.controller;

import com.alibaba.fastjson.JSON;
import com.example.baolema.DAO.OrderDao;
import com.example.baolema.bean.Activity;
import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderInformation;
import com.example.baolema.bean.OrderSum;
import com.example.baolema.util.httpUtil;

import java.util.List;

public class OrderController implements OrderDao {

    @Override
    public List<Integer> getOrderSumIdList(int userId) {
        String path="http://ylnzk.cn:8002/blm/OrderSum/getOrderSumIdList?userId="+userId;
        String result= httpUtil.getHttpInterface(path);
        List<Integer> response= JSON.parseArray(result,Integer.class);
        return  response;
    }

    @Override
    public OrderSum getOrderSumById(int orderId) {
        String path="http://ylnzk.cn:8002/blm/OrderSum/getOrderSumById?orderId="+orderId;
        String result= httpUtil.getHttpInterface(path);
        OrderSum response= JSON.parseObject(result,OrderSum.class);
        return  response;
    }

    @Override
    public List<OrderInformation> getOrderInformationList(int orderId) {
        String path="http://ylnzk.cn:8002/blm/OrderInformation/getOrderInformationList?orderId="+orderId;
        String result= httpUtil.getHttpInterface(path);
        List<OrderInformation> response=JSON.parseArray(result,OrderInformation.class);
        return  response;
    }

    @Override
    public void addOrderInf(int orderId) {
    }

    @Override
    public void updateOrderStatus(int orderId) {
        String path="http://ylnzk.cn:8002/blm/Order/updateOrderStatus?orderId="+orderId;
        httpUtil.getHttpInterface(path);
    }
}
