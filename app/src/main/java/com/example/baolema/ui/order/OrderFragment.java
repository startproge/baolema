package com.example.baolema.ui.order;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.baolema.ui.MainActivity;
import com.example.baolema.R;
import com.example.baolema.bean.OrderSum;
import com.example.baolema.bean.Shop;
import com.example.baolema.util.httpUtil;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static android.content.Context.MODE_PRIVATE;


public class OrderFragment extends Fragment {
    private String urlStr = "http://47.98.229.17:8002/blm";
    private RecyclerView recyclerView;
    private List<OrderSum> ordersSumList;
    private List<OrderSum> ordersSumStartList;
    private List<OrderSum> ordersSumFinishList;
    private Set<Integer> shopIdSet;
    private OrderMainAdapter orderMainAdapter;
    private SharedPreferences pref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.resetTitle("全部订单");

        pref = mainActivity.getSharedPreferences("user", MODE_PRIVATE);
        ordersSumList = new ArrayList<>();
        shopIdSet = new TreeSet<>();

        recyclerView = root.findViewById(R.id.recycler_order_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        ordersSumList.clear();
        shopIdSet.clear();
        Thread thread = new Thread(() -> {
            ordersSumList = JSON.parseArray(httpUtil.getHttpInterface(urlStr + "/OrderSum/getOrderSumList?userId=" + pref.getInt("userId", -1)), OrderSum.class);
            ordersSumStartList = new ArrayList<>();
            ordersSumFinishList = new ArrayList<>();
            Collections.reverse(ordersSumList);
            for (int i = 0; i < ordersSumList.size(); i++) {
                if (ordersSumList.get(i).getOrderStatus().equals("下单") || ordersSumList.get(i).getOrderStatus().equals("待取"))
                    ordersSumStartList.add(ordersSumList.get(i));
                else
                    ordersSumFinishList.add(ordersSumList.get(i));
            }
            ordersSumList = ordersSumStartList;
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
            shopIdSet.add(o.getShopId());
        for (Integer i : shopIdSet)
            getShopByHttp(i);

        Set<Integer> shopIdSet2 = new TreeSet<>();
        ordersSumList.addAll(ordersSumFinishList);
        for (OrderSum o : ordersSumList)
            shopIdSet2.add(o.getShopId());
        for (Integer i : shopIdSet2)
            getShopByHttp(i);
    }

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

    void getShopByHttp(final int id) {
        new Thread(() -> {
            Shop shop = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Shop/getShopById?shopId=" + id), Shop.class);
            for (int i = 0; i < ordersSumList.size(); i++) {
                if (ordersSumList.get(i).getShopId() == id)
                    ordersSumList.get(i).setShopTrademark(shop.getShopTrademark());
            }
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }).start();
    }
}