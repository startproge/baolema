package com.example.baolema.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.ShopCarRecipe;

import java.util.ArrayList;
import java.util.List;

public class OrderCommitActivity extends AppCompatActivity {
    private RecyclerView orderRecipesRecycleView;
    private TextView summary;
    private Double totalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_commit);

        Toolbar toolbar = findViewById(R.id.tool_bar_shop_commit);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<ShopCarRecipe> shopCarRecipes = (ArrayList<ShopCarRecipe>) args.getSerializable("orderRecipes");
        orderRecipesRecycleView = findViewById(R.id.recipe_recycleview);
        LinearLayoutManager orderLayoutManager = new LinearLayoutManager(this);
        orderRecipesRecycleView.setLayoutManager(orderLayoutManager);
        OrderCommitAdapter orderCommitAdapter = new OrderCommitAdapter(shopCarRecipes, this);
        orderRecipesRecycleView.setAdapter(orderCommitAdapter);
        summary=findViewById(R.id.total_money);
        totalMoney=0.0;
        for(int i=0;i<shopCarRecipes.size();i++)
            totalMoney+=(shopCarRecipes.get(i).getMoney()*shopCarRecipes.get(i).getNum());
        summary.setText("总计￥"+String.valueOf(totalMoney));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

class OrderCommitAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShopCarRecipe> shopCarRecipes;
    private Context context;

    static class ShopCarViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView money;
        TextView num;

        public ShopCarViewHolder(View view){
            super(view);
            name=view.findViewById(R.id.recipe_name);
            money=view.findViewById(R.id.recipe_money);
            num=view.findViewById(R.id.recipe_num);
        }
    }

    public OrderCommitAdapter (List<ShopCarRecipe> arrayList, Context context){
        if(arrayList.size()==0)
            shopCarRecipes=new ArrayList<>();
        else
            shopCarRecipes=arrayList;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_order,parent,false);
        ShopCarViewHolder holder=new ShopCarViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ShopCarViewHolder mholder=(ShopCarViewHolder) holder;
        final ShopCarRecipe shopCarRecipe=shopCarRecipes.get(position);
        mholder.name.setText( shopCarRecipe.getName());
        mholder.money.setText("￥"+(shopCarRecipe.getMoney()*shopCarRecipe.getNum()));
        mholder.num.setText("*"+Integer.toString(shopCarRecipe.getNum()));

    }

    @Override
    public int getItemCount() {
        return shopCarRecipes.size();
    }

    public List<ShopCarRecipe> getRecipes(){
        return this.shopCarRecipes;
    }
}
