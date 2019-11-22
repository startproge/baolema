package com.example.baolema.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.MainActivity;
import com.example.baolema.R;
import com.example.baolema.bean.OrderMain;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private OrderViewModel orderViewModel;
    private List<OrderMain> ordersList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                ViewModelProviders.of(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.resetTitle("我的订单");

        initOrderList();

        RecyclerView recyclerView = root.findViewById(R.id.recycler_order_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new OrderMainAdapter(ordersList));

        return root;
    }

    public void initOrderList() {
        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
        ordersList.add(new OrderMain("学院学院", 32.94, "已完成"));
        ordersList.add(new OrderMain("学院学院", 2.94, "待评价"));
        ordersList.add(new OrderMain("学院学院", 329.4, "待评价"));
        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
        ordersList.add(new OrderMain("学院学院", 2, "待评价"));
        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
    }
}