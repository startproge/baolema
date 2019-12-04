package com.example.baolema.ui.order;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.OrderSum;
import com.example.baolema.ui.home.HomeRecyclerAdapter;
import com.example.baolema.ui.home.ShopCarAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderMainAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
    private List<OrderSum> orderList;
    private OrderMainAdapter.OnRecycleItemClickListener onRecycleItemClickListener = null;

    public OrderMainAdapter(List<OrderSum> orderList) {
        this.orderList = orderList;
    }

    @Override
    public void onClick(View v) {
        if (onRecycleItemClickListener != null)
            onRecycleItemClickListener.OnRecycleItemClickListener(v, (Integer) v.getTag());

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_order_main, parent, false);
        RecyclerView.ViewHolder viewHolder = new OrderMainViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderMainViewHolder orderMainViewHolder = (OrderMainViewHolder) holder;
        if (orderList.get(position).getShopName() != null)
            orderMainViewHolder.textShopName.setText(orderList.get(position).getShopName());
        if (orderList.get(position).getOrderStatus() != null)
            orderMainViewHolder.textOrderStatus.setText(orderList.get(position).getOrderStatus());
        if (orderList.get(position).getOrdersum() != null)
            orderMainViewHolder.orderSumPrice.setText(String.valueOf(orderList.get(position).getOrdersum()));
        if (orderList.get(position).getShopTrademark() != null && orderList.get(position).getShopTrademark().length > 0)
            orderMainViewHolder.imageShop.setImageBitmap(BitmapFactory.decodeByteArray(orderList.get(position).getShopTrademark(), 0, orderList.get(position).getShopTrademark().length));
        if (orderList.get(position).getOrderStartTime() != null)
            orderMainViewHolder.orderTime.setText(format.format(orderList.get(position).getOrderStartTime()));
        orderMainViewHolder.itemView.setTag(position);

    }

    public interface OnRecycleItemClickListener {
        void OnRecycleItemClickListener(View view, int position);

    }

    public void OnRecycleItemClickListener(OrderMainAdapter.OnRecycleItemClickListener v) {
        onRecycleItemClickListener = v;
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}