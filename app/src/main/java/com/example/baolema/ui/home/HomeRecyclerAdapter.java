package com.example.baolema.ui.home;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.Shop;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private List<Shop> shopList;
    private Context mContext;
    private HomeRecyclerAdapter.OnRecycleItemClickListener onRecycleItemClickListener = null;

    public HomeRecyclerAdapter(List<Shop> shopList, Context mContext) {
        this.shopList = shopList;
        this.mContext = mContext;
    }

    @Override
    public void onClick(View v) {
        if (onRecycleItemClickListener != null)
            onRecycleItemClickListener.OnRecycleItemClickListener(v, (Integer) v.getTag());
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView shopTrademark;
        TextView shopName;
        TextView shopMonthSale;
        TextView shopStatus;
        List<ImageView> shopGradeList = new ArrayList<>(5);

        ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            this.shopTrademark = itemView.findViewById(R.id.image_shop);
            this.shopName = itemView.findViewById(R.id.text_shop_name);
            this.shopMonthSale = itemView.findViewById(R.id.text_shop_month_sale);
            this.shopStatus = itemView.findViewById(R.id.text_shop_status);
            shopGradeList.add(itemView.findViewById(R.id.image_star_1));
            shopGradeList.add(itemView.findViewById(R.id.image_star_2));
            shopGradeList.add(itemView.findViewById(R.id.image_star_3));
            shopGradeList.add(itemView.findViewById(R.id.image_star_4));
            shopGradeList.add(itemView.findViewById(R.id.image_star_5));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_shop, parent, false);
        RecyclerView.ViewHolder viewHolder = new ShopViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ShopViewHolder shopViewHolder = (ShopViewHolder) holder;

        Shop shop = shopList.get(position);
        if (shop.getShopTrademark() != null && shop.getShopTrademark().length > 0)
            shopViewHolder.shopTrademark.setImageBitmap(BitmapFactory.decodeByteArray(shop.getShopTrademark(), 0, shop.getShopTrademark().length));
        shopViewHolder.shopName.setText(shop.getShopName());
        shopViewHolder.shopStatus.setText(shop.getShopStatus());
        for (int i = 0; i < getGrades(shop.getShopCore()); ++i)
            shopViewHolder.shopGradeList.get(i).setVisibility(View.VISIBLE);
        shopViewHolder.shopMonthSale.setText("月售" + shop.getShopMonthSale() + "单");
        shopViewHolder.itemView.setTag(position);
    }

    private static int getGrades(double grades) {
        return (int) Math.round(grades);
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public void OnRecycleItemClickListener(HomeRecyclerAdapter.OnRecycleItemClickListener v) {
        onRecycleItemClickListener = v;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface OnRecycleItemClickListener {
        void OnRecycleItemClickListener(View view,int position);
    }

    public List<Shop> getShopList() {
        return shopList;
    }
}
