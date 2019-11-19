package com.example.baolema.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static class RecipeViewHolder extends RecyclerView.ViewHolder{
        public RecipeViewHolder(View view){
            super(view);
        }
    }

    public RecipeAdapter(){
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_recipe,parent,false);
        RecipeViewHolder holder=new RecipeViewHolder(view);
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
