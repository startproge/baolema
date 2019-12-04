package com.example.baolema.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.baolema.R;

public class OrderStatusFragment extends Fragment {

    private String orderStatus;

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

        point3=view.findViewById(R.id.point3);
        shop_order_finish=view.findViewById(R.id.shop_order_finish);
        divide3=view.findViewById(R.id.divide3);
        point4=view.findViewById(R.id.point4);
        shop_order_over=view.findViewById(R.id.shop_order_over);
        finish=view.findViewById(R.id.finish);

        if(orderStatus!=null){
            if(orderStatus.equals("完成")){
                point3.setVisibility(View.VISIBLE);
                shop_order_finish.setVisibility(View.VISIBLE);
                divide3.setVisibility(View.VISIBLE);
            }
            else if(orderStatus.equals("待自提")){
                point3.setVisibility(View.VISIBLE);
                shop_order_finish.setVisibility(View.VISIBLE);
                divide3.setVisibility(View.VISIBLE);
                point4.setVisibility(View.VISIBLE);
                shop_order_over.setVisibility(View.VISIBLE);
                finish.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }
}
