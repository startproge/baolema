package com.example.baolema.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.ShopCarRecipe;

import java.util.ArrayList;

public class ShopCarAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ShopCarRecipe> mArrayList;
    private Context context;

    static class ShopCarViewHolder extends RecyclerView.ViewHolder{

        public ShopCarViewHolder(View view){
            super(view);

        }
    }

    public ShopCarAdapter (ArrayList<ShopCarRecipe> arrayList, Context context){
        if(arrayList.size()==0)
            mArrayList=new ArrayList<>();
        else
            mArrayList=arrayList;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_recipe,parent,false);
        RecipeAdapter.RecipeViewHolder holder=new RecipeAdapter.RecipeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 15;
    }
}