package com.example.baolema.ui.order;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.baolema.R;
import com.example.baolema.controller.OrderController;

public class OrderStatusFragment extends Fragment {

    private String orderStatus;
    private int orderId;

    private ImageView point3;
    private TextView shop_order_finish;
    private ImageView divide3;

    private ImageView point4;
    private TextView shop_order_over;

    private Button finish;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order_status,container,false);
        Bundle bundle= OrderStatusFragment.this.getArguments();
        orderStatus=bundle.getString("orderStatus");
        orderId=bundle.getInt("orderId");
        point3=view.findViewById(R.id.point3);
        shop_order_finish=view.findViewById(R.id.shop_order_finish);
        divide3=view.findViewById(R.id.divide3);
        point4=view.findViewById(R.id.point4);
        shop_order_over=view.findViewById(R.id.shop_order_over);
        finish=view.findViewById(R.id.finish);

        if(orderStatus!=null){
            if(orderStatus.equals("待取")){
                point3.setVisibility(View.VISIBLE);
                shop_order_finish.setVisibility(View.VISIBLE);
                divide3.setVisibility(View.VISIBLE);
                finish.setVisibility(View.VISIBLE);
            }
            else if(orderStatus.equals("完成")){
                point3.setVisibility(View.VISIBLE);
                shop_order_finish.setVisibility(View.VISIBLE);
                divide3.setVisibility(View.VISIBLE);
                point4.setVisibility(View.VISIBLE);
                shop_order_over.setVisibility(View.VISIBLE);
                //finish.setVisibility(View.VISIBLE);
            }
        }

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ThreadUpdateStatus thread=new ThreadUpdateStatus();
                    thread.start();
                    thread.join();
                    Toast.makeText(getActivity(),"收货成功",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getActivity(),"收货失败",Toast.LENGTH_SHORT).show();
                }
                    finish.setVisibility(View.GONE);
                    point4.setVisibility(View.VISIBLE);
                    shop_order_over.setVisibility(View.VISIBLE);

            }
        });
        return view;
    }

    private class  ThreadUpdateStatus extends Thread{
        @Override
        public void run() {
            new OrderController().updateOrderStatus(orderId);
        }
    }
}
