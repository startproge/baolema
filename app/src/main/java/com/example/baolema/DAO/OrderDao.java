package com.example.baolema.DAO;

import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderMain;

import java.util.ArrayList;

public interface OrderDao {
    void addOrderMain(OrderMain orderMain);
    void addOrderInf(ArrayList<OrderInf> orderInf);
}
