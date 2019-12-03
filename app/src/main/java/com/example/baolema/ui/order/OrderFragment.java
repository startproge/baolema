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
import com.example.baolema.bean.OrderSum;
import com.example.baolema.util.httpUtil;


import java.util.ArrayList;
import java.util.List;


public class OrderFragment extends Fragment {
    private String urlStr = "http://47.98.229.17:8002/blm";
    private OrderViewModel orderViewModel;
    private RecyclerView recyclerView;
    private int userId = 1;
    private List<OrderSum> ordersSumList = new ArrayList<>();
    private List<Integer> ordersSumIdList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    recyclerView.setAdapter(new OrderMainAdapter(ordersSumList));
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

    private void getOrderListByHttp() {
        new Thread(() -> {
            ordersSumIdList = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Order/"), new TypeReference<>());

            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }).start();
    }

    private void getOrderIdListByHttp(int shopId) {
        new Thread(() -> {
            ordersSumList.add(JSON.parseObject(httpUtil.getHttpInterface(urlStr + " "), OrderSum.class));

            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }).start();
    }
}