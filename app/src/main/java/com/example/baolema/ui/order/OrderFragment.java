package com.example.baolema.ui.order;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.baolema.MainActivity;
import com.example.baolema.R;
import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderMain;
import com.example.baolema.bean.Orders;
import com.example.baolema.bean.Shop;
import com.example.baolema.util.httpUtil;


import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private String urlStr = "http://47.98.229.17:8002/blm";
    private OrderViewModel orderViewModel;
    private RecyclerView recyclerView;
    private int userId = 1;
    private List<Orders> ordersList = new ArrayList<>();
    private List<OrderMain> mainList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    recyclerView.setAdapter(new OrderMainAdapter(mainList));
                    break;
                default:
                    break;
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                ViewModelProviders.of(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.resetTitle("我的订单");

//        initOrderList();

        recyclerView = root.findViewById(R.id.recycler_order_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getOrderListByHttp();
//        recyclerView.setAdapter(new OrderMainAdapter(ordersList));

        return root;
    }

    void getOrderListByHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ordersList = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Order/getOrderList?userId=" + userId), new TypeReference<List<Orders>>() {});
                List<Shop> shopList = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "Shop/getShopList"), new TypeReference<List<Shop>>() {});
                for (int i = 0; i < ordersList.size(); i++) {
                    mainList.add(new OrderMain(ordersList.get(i)));
                    Shop shop = findShop(mainList.get(i).getShopId(), shopList);
                    mainList.get(i).setShopName(shop.getShopName());
//                    mainList.get(i).setShopTradeMark(shop.getShopTrademark());
                    mainList.get(i).setOrderInfList(JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/OrderInf/getOrderInfList?orderId=" + ordersList.get(i).getOrderId()), new TypeReference<List<OrderInf>>() {}));
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }).start();
    }

    Shop findShop(int shopId, List<Shop> shopList) {
        for (Shop s : shopList) {
            if (s.getShopId() == shopId)
                return s;
        }
        return null;
    }

//    public void initOrderList() {
//        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
//        ordersList.add(new OrderMain("学院学院", 32.94, "已完成"));
//        ordersList.add(new OrderMain("学院学院", 2.94, "待评价"));
//        ordersList.add(new OrderMain("学院学院", 329.4, "待评价"));
//        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
//        ordersList.add(new OrderMain("学院学院", 2, "待评价"));
//        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
//        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
//        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
//    }
}