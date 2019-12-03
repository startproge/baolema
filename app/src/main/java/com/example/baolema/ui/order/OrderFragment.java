package com.example.baolema.ui.order;

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
    private List<OrderSum> ordersSumList;
    private List<Integer> ordersSumIdList;
    private OrderMainAdapter orderMainAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
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

        ordersSumIdList = new ArrayList<>();
        ordersSumList = new ArrayList<>();

        recyclerView = root.findViewById(R.id.recycler_order_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderMainAdapter = new OrderMainAdapter(ordersSumList);
        recyclerView.setAdapter(orderMainAdapter);

        getOrderIdListByHttp();
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            Log.e(e.getLocalizedMessage(), "onCreateView: ");
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        ordersSumList.clear();
        for (Integer id : ordersSumIdList)
            getOrderSumByHttp(id);
    }

    void getOrderIdListByHttp() {
        new Thread(() -> {
            ordersSumIdList = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Order/"), new TypeReference<>());
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }).start();
    }

    void getOrderSumByHttp(int shopId) {
        new Thread(() -> {
            ordersSumList.add(JSON.parseObject(httpUtil.getHttpInterface(urlStr + " " + shopId), OrderSum.class));

            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }).start();
    }
}