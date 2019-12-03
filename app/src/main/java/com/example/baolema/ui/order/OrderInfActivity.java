package com.example.baolema.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.baolema.R;
import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderSum;
import com.example.baolema.bean.Recipe;
import com.example.baolema.bean.ShopCarRecipe;
import com.example.baolema.util.httpUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderInfActivity extends AppCompatActivity implements View.OnClickListener{
    private Fragment order_status;
    private Fragment order_inf;
    private int orderId;
    private Button button_order_status;
    private ArrayList<ShopCarRecipe> orderRecipes;
    private Button button_order_inf;
    private OrderSum orderSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail_main);
        Intent intent = getIntent();
        orderSum=(OrderSum)intent.getSerializableExtra("orderSum");

        if (orderSum==null){
            Bundle args = intent.getBundleExtra("OrderCommitToOrderInf");
            orderRecipes = (ArrayList<ShopCarRecipe>) args.getSerializable("orderRecipes");
        }
        else {
            orderRecipes=new ArrayList<ShopCarRecipe>();
        }

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
            Bundle bundle=new Bundle();
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
            Bundle bundle=new Bundle();
            if (orderSum==null) {
                bundle.putInt("orderId", 0);
                bundle.putSerializable("orderRecipes", (Serializable) orderRecipes);
            }
             else {
                bundle.putInt("orderId", orderSum.getOrderId());
                bundle.putSerializable("orderSum",orderSum);
            }
            order_inf.setArguments(bundle);
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
