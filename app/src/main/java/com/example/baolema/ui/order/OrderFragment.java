package com.example.baolema.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.baolema.MainActivity;
import com.example.baolema.R;
import com.example.baolema.bean.OrderSum;
import com.example.baolema.controller.OrderController;
import com.example.baolema.util.httpUtil;


import java.util.ArrayList;
import java.util.List;


public class OrderFragment extends Fragment {
    private String urlStr = "http://47.98.229.17:8002/blm";
    private OrderViewModel orderViewModel;
    private RecyclerView recyclerView;
    private int userId = 1;
    private List<OrderSum> ordersSumList;
    private OrderMainAdapter orderMainAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    orderMainAdapter = new OrderMainAdapter(ordersSumList);
                    recyclerView.setAdapter(orderMainAdapter);
                    orderMainAdapter.OnRecycleItemClickListener((view, position) -> {
                                Intent intent = new Intent(getActivity(), OrderInfActivity.class);
                                OrderSum orderSum = ordersSumList.get(position);
                                intent.putExtra("orderSum", orderSum);
                                startActivity(intent);
                            }
                    );
                    break;
                case 2:
                    orderMainAdapter.notifyDataSetChanged();
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

        ordersSumList = new ArrayList<>();

        recyclerView = root.findViewById(R.id.recycler_order_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        ordersSumList.clear();
        Thread thread = new Thread(() -> {
            ordersSumList = JSON.parseArray(httpUtil.getHttpInterface(urlStr + "/OrderSum/getOrderSumList?userId="+userId), OrderSum.class);
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (OrderSum o : ordersSumList)
            getOrderSumByHttp(o.getOrderId());
    }

    void getOrderSumByHttp(int id) {
        new Thread(() -> {
            OrderSum orderSum = new OrderController().getOrderSumById(id);
            for (int i = 0; i < ordersSumList.size(); i++)
                if (ordersSumList.get(i).getOrderId() == id)
                    ordersSumList.get(i).setShopTrademark(orderSum.getShopTrademark());
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }).start();
    }
}