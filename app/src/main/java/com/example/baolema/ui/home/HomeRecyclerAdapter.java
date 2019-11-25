package com.example.baolema.ui.home;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.Shop;

import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter {
    private List<Shop> shopList;

    public HomeRecyclerAdapter(List<Shop> shopList) {
        this.shopList = shopList;
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView shopTrademark;
        TextView shopName;
        TextView shopMonthSale;

        ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            this.shopTrademark = itemView.findViewById(R.id.image_shop);
            this.shopName = itemView.findViewById(R.id.text_shop_name);
            this.shopMonthSale = itemView.findViewById(R.id.text_shop_month_sale);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShopViewHolder shopViewHolder = (ShopViewHolder) holder;
        Shop shop = shopList.get(position);
//        shopViewHolder.shopTrademark.setImageResource(R.drawable.ic_icon_shop_phone);
        shopViewHolder.shopName.setText(shop.getShopName());
        shopViewHolder.shopMonthSale.setText("月售" + shop.getShopMonthSale() + "单");

        //评分显示
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }
}
