package com.example.baolema.ui.home;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.Activity;
import com.example.baolema.bean.Shop;
import com.example.baolema.controller.ActivityController;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<Shop> shopList;
    private Context mContext;
    private HomeRecyclerAdapter.OnRecycleItemClickListener onRecycleItemClickListener = null;
    List<Activity> activities;
    public HomeRecyclerAdapter(List<Activity> activityList,List<Shop> shopList, Context mContext) {
        this.shopList = shopList;
        this.mContext = mContext;
        this.activities=activityList;
    }

    @Override
    public void onClick(View v) {
        if (onRecycleItemClickListener != null)
            onRecycleItemClickListener.OnRecycleItemClickListener(v, (Integer) v.getTag());
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView shopTrademark;
        TextView shopName;
        TextView shopMonthSale;
        TextView shopStatus;
        TextView shopScore;
        TextView shopRate;
        TextView shopActivity;
        TextView shopDistance;
        List<ImageView> shopGradeList = new ArrayList<>(5);

        ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            this.shopScore=itemView.findViewById(R.id.shop_score);
            this.shopTrademark = itemView.findViewById(R.id.image_shop);
            this.shopName = itemView.findViewById(R.id.text_shop_name);
            this.shopMonthSale = itemView.findViewById(R.id.text_shop_month_sale);
            this.shopStatus = itemView.findViewById(R.id.text_shop_status);
            this.shopRate=itemView.findViewById(R.id.shop_rate);
            this.shopActivity=itemView.findViewById(R.id.fullcount);
            this.shopDistance=itemView.findViewById(R.id.distance);
            shopGradeList.add(itemView.findViewById(R.id.image_star_1));
//            shopGradeList.add(itemView.findViewById(R.id.image_star_2));
//            shopGradeList.add(itemView.findViewById(R.id.image_star_3));
//            shopGradeList.add(itemView.findViewById(R.id.image_star_4));
//            shopGradeList.add(itemView.findViewById(R.id.image_star_5));
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
        if (shop.getShopStatus().equals("离线"))
            shopViewHolder.shopStatus.setText(shop.getShopStatus());
        shopViewHolder.shopScore.setText(getGrades(shop.getShopCore()));
        shopViewHolder.shopMonthSale.setText("月售" + shop.getShopMonthSale());
        shopViewHolder.shopRate.setText("“"+shop.getShopNotice()+"”");
        shopViewHolder.itemView.setTag(position);
        int flag=0;
        Log.e("onList", activities.size()+"" );
        for(Activity activity:activities){
//            Log.e("activity", activity.getActivityId()+" "+activity.getShopId()+" "+activity.getFullMoney()+" "+activity.getReduceMoney()+"");
//            Log.e("position"+shop.getShopId()+"   "+activity.getShopId(), "onBindViewHolder");
            if(shop.getShopId() == activity.getShopId()){
                shopViewHolder.shopActivity.setText((int)activity.getFullMoney() + "减" + (int)activity.getReduceMoney());
                flag=1;
            }
        }
        if(flag==0)
            shopViewHolder.shopActivity.setVisibility(View.GONE);

        double dis=Double.valueOf(getGrades(Math.random()*5));
        shopViewHolder.shopDistance.setText((int)(15*dis)+"分钟 "+dis+"km");
    }

    private static String getGrades(double grades) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(grades);
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
        void OnRecycleItemClickListener(View view, int position);
    }

    public List<Shop> getShopList() {
        return shopList;
    }
}
