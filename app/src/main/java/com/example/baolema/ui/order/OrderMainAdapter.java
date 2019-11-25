package com.example.baolema.ui.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.OrderMain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderMainAdapter extends RecyclerView.Adapter {
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
    private List<OrderMain> orderList;

    public OrderMainAdapter(List<OrderMain> orderList) {
        this.orderList = orderList;
    }

    static class OrderMainViewHolder extends RecyclerView.ViewHolder {
        TextView textShopName;
        TextView textOrderStatus;
        ImageView imageShop;
        TextView orderSumPrice;
        TextView orderTime;

        OrderMainViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textShopName = itemView.findViewById(R.id.text_order_main_shop);
            this.textOrderStatus = itemView.findViewById(R.id.text_order_main_evaluate);
            this.imageShop = itemView.findViewById(R.id.image_order_main);
            this.orderSumPrice = itemView.findViewById(R.id.text_order_main_price);
            this.orderTime = itemView.findViewById(R.id.text_order_main_time);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderMainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_order_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderMainViewHolder orderMainViewHolder = (OrderMainViewHolder) holder;
        orderMainViewHolder.textShopName.setText(orderList.get(position).getShopName());
        orderMainViewHolder.textOrderStatus.setText(orderList.get(position).getOrderStatus());
        orderMainViewHolder.orderSumPrice.setText(String.valueOf(orderList.get(position).getOrderPrice()));
//        orderMainViewHolder.imageShop.setImageBitmap();
        orderMainViewHolder.orderTime.setText(format.format(orderList.get(position).getOrderStartTime()));

        //评分显示
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}