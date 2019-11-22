package com.example.baolema.ui.home;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.Recipe;
import com.example.baolema.bean.ShopCarRecipe;

import java.util.ArrayList;

public class ShopCarAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ShopCarRecipe> shopCarRecipes;
    private Context context;

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
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_car_recycler,parent,false);
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
                notifyDataSetChanged();
            }
        });
        mholder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=shopCarRecipe.getNum()-1;
                //if(num==0)
                shopCarRecipe.setNum(num);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopCarRecipes.size();
    }

    public ArrayList<ShopCarRecipe> getRecipes(){
        return this.shopCarRecipes;
    }
}