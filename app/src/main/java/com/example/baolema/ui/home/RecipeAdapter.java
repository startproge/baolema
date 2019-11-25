package com.example.baolema.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnRecycleItemClickListener onRecycleItemClickListener=null;
    private List<Recipe> recipes;
    private Context context;

    static class RecipeViewHolder extends RecyclerView.ViewHolder{
        private ImageView add;
        private TextView name;
        private TextView notice;
        private TextView monthlySale;
        private TextView money;
        public RecipeViewHolder(View view){
            super(view);
            add=view.findViewById(R.id.recipe_add);
            /*name=view.findViewById(R.id.recipe_name);
            money=view.findViewById(R.id.recipe_money);
            notice=view.findViewById(R.id.recipe_information);
            monthlySale=view.findViewById(R.id.recipe_monthly_sale);*/
        }
    }

    /*public RecipeAdapter(List<Recipe> recipes ,Context context){
       if(recipes==null) {
            this.recipes=new ArrayList<Recipe>();
        }
        else
            this.recipes=recipes;
        this.context=context;
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_recipe,parent,false);
        RecipeViewHolder holder=new RecipeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecipeViewHolder mHolder=(RecipeViewHolder)holder;
        /*Recipe recipe=recipes.get(position);
        mHolder.name.setText(recipe.getRecipeName());
        mHolder.money.setText(Double.toString(recipe.getRecipePrice()));
        mHolder.monthlySale.setText(Integer.toString(recipe.getMonthlySale()));
        mHolder.notice.setText(recipe.getRecipeNotice());*/
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
        //return recipes.size();
        return 10;
    }

    public  void  OnRecycleItemClickListener(OnRecycleItemClickListener v){
        onRecycleItemClickListener=v;
    }
    public interface OnRecycleItemClickListener{
        void OnRecycleItemClickListener(int position);
    }



}
