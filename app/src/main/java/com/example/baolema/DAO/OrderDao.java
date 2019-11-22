package com.example.baolema.DAO;

import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderMain;

import java.util.List;

public interface OrderDao {
    // url?type=android&req=orderList&userId=
    List<OrderMain> getOrderList(int userId);

    // url?type=android&req=orderInfList&orderId=
    List<OrderInf> getOrderInfList(int orderId);

    //客户端发送先不写
//    void addOrderMain(OrderMain orderMain);
//    void addOrderInf(ArrayList<OrderInf> orderInf);
}
