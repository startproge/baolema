package com.example.baolema.DAO;

import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderMain;
import com.example.baolema.bean.Orders;

import java.util.List;

public interface OrderDao {
    /**
     * @url http://47.98.229.17:8002/blm
     */

    // url/Order/getOrderList?userId=
    List<Orders> getOrderList(int userId);

    // url/OrderInf/getOrderInfList?orderId=
    List<OrderInf> getOrderInfList(int orderId);

    //客户端发送先不写
//    void addOrderMain(OrderMain orderMain);
//    void addOrderInf(ArrayList<OrderInf> orderInf);
}
