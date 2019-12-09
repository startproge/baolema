package com.example.baolema.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.Activity;
import com.example.baolema.bean.ShopCarRecipe;

import java.util.ArrayList;
import java.util.List;

public class ShopCarAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnRecycleItemClickListener onRecycleItemClickListener=null;
    private ArrayList<ShopCarRecipe> shopCarRecipes;
    private List<Activity> activities;
    private Context context;
    private Double money;
    private Double reduce;

    static class ShopCarViewHolder extends RecyclerView.ViewHolder{
         TextView name;
         TextView money;
         TextView num;
         Button add;
         Button reduce;
        public ShopCarViewHolder(View view){
            super(view);
            name=view.findViewById(R.id.recipe_name);
            money=view.findViewById(R.id.recipe_money);
            num=view.findViewById(R.id.recipe_num);
            add=view.findViewById(R.id.recipe_add);
            reduce=view.findViewById(R.id.recipe_reduce);
        }
    }

    public ShopCarAdapter (ArrayList<ShopCarRecipe> arrayList, Context context){
        if(arrayList.size()==0)
            shopCarRecipes=new ArrayList<>();
        else
            shopCarRecipes=arrayList;
        activities=new ArrayList<>();
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_shop_car,parent,false);
        ShopCarViewHolder holder=new ShopCarViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ShopCarViewHolder mholder=(ShopCarViewHolder) holder;
        final ShopCarRecipe shopCarRecipe=shopCarRecipes.get(position);
        mholder.name.setText( shopCarRecipe.getName());
        mholder.money.setText("￥"+shopCarRecipe.getMoney());
        mholder.num.setText(Integer.toString(shopCarRecipe.getNum()));
        mholder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=shopCarRecipe.getNum()+1;
                shopCarRecipe.setNum(num++);
                resetMoney();
                resetReduce();
                //notifyDataSetChanged();
                if(onRecycleItemClickListener!=null)
                    onRecycleItemClickListener.OnRecycleItemClickListener(position);
            }
        });
        mholder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=shopCarRecipe.getNum()-1;
                if(num==0)
                   shopCarRecipes.remove(position);
                else
                   shopCarRecipe.setNum(num);
                resetMoney();
                resetReduce();
                if(onRecycleItemClickListener!=null)
                    onRecycleItemClickListener.OnRecycleItemClickListener(position);
                //notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopCarRecipes.size();
    }

    public ArrayList<ShopCarRecipe> getShopCarRecipes(){
        return this.shopCarRecipes;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Double getMoney() {
        return money;
    }

    public void resetMoney(){
        this.money=0.0;
        for(int i=0;i<shopCarRecipes.size();i++)
            this.money+=shopCarRecipes.get(i).getMoney()*shopCarRecipes.get(i).getNum();
    }

    public Double getReduce() {
        return reduce;
    }

    public void resetReduce() {
        this.reduce = 0.0;
        //Log.d("activities.size",String.valueOf(activities.get(0).getFullMoney()));
        for(int i=0;i<activities.size();i++){
            if(this.money>activities.get(i).getFullMoney()&&this.reduce<activities.get(i).getReduceMoney())
                this.reduce=activities.get(i).getReduceMoney();
        }
    }

    public void deleteRecipe(int recipeId){
        int position=-1;
        for(int i=0;i<this.shopCarRecipes.size();i++){
            if(this.shopCarRecipes.get(i).getRecipeId()==recipeId) {
                position = i;
                break;
            }
        }
        if(position!=-1)
            this.shopCarRecipes.remove(position);

    }

    public String getRecipeName(int recipeId){
        for(int i=0;i<this.shopCarRecipes.size();i++){
            if(this.shopCarRecipes.get(i).getRecipeId()==recipeId) {
                    return shopCarRecipes.get(i).getName();
            }
        }
        return null;
    }



    public  void  OnRecycleItemClickListener(OnRecycleItemClickListener v){
        onRecycleItemClickListener=v;
    }

    public interface OnRecycleItemClickListener{
        void OnRecycleItemClickListener(int position);
    }
}