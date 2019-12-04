package com.example.baolema.DAO;

import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderSum;

import java.util.List;

public interface OrderDao {
    /**
     * @url http://47.98.229.17:8002/blm
     */

    // url/Order/getOrderSumIdList?userId=
    List<Integer> getOrderSumIdList(int userId);

    OrderSum getOrderSumById(int orderId);

    List<OrderInf> getOrderInfList(int orderId);

    //客户端发送先不写
//    void addOrder(Orders orders);
//    void addOrderInf(ArrayList<OrderInf> orderInf);
}
