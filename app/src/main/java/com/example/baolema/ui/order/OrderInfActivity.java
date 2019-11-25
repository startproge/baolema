package com.example.baolema.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.baolema.R;

public class OrderInfActivity extends AppCompatActivity implements View.OnClickListener{
    private Fragment order_status;
    private Fragment order_inf;

    private Button button_order_status;
    private Button button_order_inf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail_main);
        button_order_status=findViewById(R.id.orderdetail_status);
        button_order_inf=findViewById(R.id.orderdetail_detail);
        button_order_status.setOnClickListener(this);
        button_order_inf.setOnClickListener(this);
        ShowFragmentOrderStatus();
    }

    private void ShowFragmentOrderStatus(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(order_status == null){
            order_status = new OrderStatusFragment();
            transaction.add(R.id.order_main_frame_layout, order_status);
        }
        hideFragment(transaction);
        transaction.show(order_status);
        transaction.commit();
    }

    private void ShowFragmentOrderInf(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(order_inf == null){
            order_inf = new OrderInfFragment();
            transaction.add(R.id.order_main_frame_layout, order_inf);
        }
        hideFragment(transaction);
        transaction.show(order_inf);
        transaction.commit();
    }


    private void hideFragment(FragmentTransaction transaction){
        if(order_status != null){
            transaction.hide(order_status);
        }
        if(order_inf != null){
            transaction.hide(order_inf);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == button_order_status){
            ShowFragmentOrderStatus();
        }else if(v == button_order_inf){
            ShowFragmentOrderInf();
        }
    }

}
