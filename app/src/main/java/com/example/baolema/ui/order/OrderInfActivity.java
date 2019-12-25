package com.example.baolema.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.baolema.R;
import com.example.baolema.bean.OrderSum;
import com.example.baolema.bean.ShopCarRecipe;
import com.example.baolema.controller.OrderController;

import java.util.ArrayList;

public class OrderInfActivity extends AppCompatActivity implements View.OnClickListener {
    private Fragment order_status;
    private Fragment order_inf;
    private Button button_order_status;
    private ArrayList<ShopCarRecipe> orderRecipes;
    private Button button_order_inf;
    private OrderSum orderSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail_main);
        Toolbar toolbar = findViewById(R.id.tool_bar_order_inf);
        Intent intent = getIntent();
        orderSum = (OrderSum) intent.getSerializableExtra("orderSum");
        toolbar.setTitle(orderSum.getShopName());
        toolbar.setNavigationOnClickListener(v -> finish());

        button_order_status = findViewById(R.id.orderdetail_status);
        button_order_inf = findViewById(R.id.orderdetail_detail);
        button_order_status.setOnClickListener(this);
        button_order_inf.setOnClickListener(this);
        ShowFragmentOrderStatus();
    }

    private void ShowFragmentOrderStatus() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (order_status == null) {
            order_status = new OrderStatusFragment();
            if (orderSum != null) {
                Bundle bundle = new Bundle();
                bundle.putString("orderStatus", orderSum.getOrderStatus());
                bundle.putInt("orderId", orderSum.getOrderId());
                order_status.setArguments(bundle);
            }
            transaction.add(R.id.order_main_frame_layout, order_status);
        }
        hideFragment(transaction);
        transaction.show(order_status);
        transaction.commit();
    }

    private void ShowFragmentOrderInf() {
        Log.d("OrderCommitStatus", orderSum.getOrderStatus());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(order_inf != null)
            transaction.remove(order_inf);

        order_inf = new OrderInfFragment();
        Bundle bundle = new Bundle();
        if (orderSum != null) {
            bundle.putInt("orderId", orderSum.getOrderId());
            bundle.putString("orderStatus", orderSum.getOrderStatus());
        }
        order_inf.setArguments(bundle);
        transaction.add(R.id.order_main_frame_layout, order_inf);
        hideFragment(transaction);
        transaction.show(order_inf);
        transaction.commit();
    }


    private void hideFragment(FragmentTransaction transaction) {
        if (order_status != null) {
            transaction.hide(order_status);
        }
        if (order_inf != null) {
            transaction.hide(order_inf);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == button_order_status) {
            ShowFragmentOrderStatus();
        } else if (v == button_order_inf) {
            try {
                ThreadgetOrderSum order = new ThreadgetOrderSum();
                order.start();
                order.join();
            } catch (Exception e) {
            }
            ShowFragmentOrderInf();
        }
    }

    private class ThreadgetOrderSum extends Thread {
        @Override
        public void run() {
            orderSum = new OrderController().getOrderSumById(orderSum.getOrderId());
            if (orderSum != null)
                Log.d("CommitStatus", String.valueOf(1));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "you use the permission", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }*/
    }

}


