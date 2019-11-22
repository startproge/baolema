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

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnRecycleItemClickListener onRecycleItemClickListener=null;
    private ArrayList<Recipe> recipes;
    private Context context;

    static class RecipeViewHolder extends RecyclerView.ViewHolder{
        private ImageView add;
        private TextView name;
        private TextView money;
        public RecipeViewHolder(View view){
            super(view);
            add=view.findViewById(R.id.recipe_add);
            name=view.findViewById(R.id.recipe_name);
            money=view.findViewById(R.id.recipe_money);
        }
    }

    public RecipeAdapter(){

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_recycleview,parent,false);
        RecipeViewHolder holder=new RecipeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecipeViewHolder mHolder=(RecipeViewHolder)holder;
        mHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRecycleItemClickListener!=null)
                    onRecycleItemClickListener.OnRecycleItemClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public  void  OnRecycleItemClickListener(OnRecycleItemClickListener v){
        onRecycleItemClickListener=v;
    }
    public interface OnRecycleItemClickListener{
        void OnRecycleItemClickListener(int position);
    }


}
