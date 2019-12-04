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
import com.example.baolema.ui.order.OrderInfActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderCommitActivity extends AppCompatActivity {
    private RecyclerView orderRecipesRecycleView;
    private ArrayList<ShopCarRecipe> shopCarRecipes;
    private int orderId;
    private Button orderCommit;
    private TextView summary;
    private TextView reduce;
    private TextView total_money;
    private TextView finally_paid;
    private TextView finally_reduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_commit);

        Toolbar toolbar = findViewById(R.id.tool_bar_shop_commit);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("ShopCarToOrderCommit");
        shopCarRecipes = (ArrayList<ShopCarRecipe>) args.getSerializable("ShopCarRecipes");
        orderRecipesRecycleView = findViewById(R.id.recipe_recycleview);
        LinearLayoutManager orderLayoutManager = new LinearLayoutManager(this);
        orderRecipesRecycleView.setLayoutManager(orderLayoutManager);
        OrderCommitAdapter orderCommitAdapter = new OrderCommitAdapter(shopCarRecipes, this);
        orderCommitAdapter.resetMoney();
        orderRecipesRecycleView.setAdapter(orderCommitAdapter);
        summary=findViewById(R.id.total_money);
        reduce=findViewById(R.id.reduce_money);
        total_money=findViewById(R.id.total_paid_money);
        finally_paid=findViewById(R.id.finally_pay_money);
        finally_reduce=findViewById(R.id.finally_reduce_money);
        orderCommitAdapter.resetMoney();
        orderCommitAdapter.resetReduce(orderCommitAdapter.getMoney());
        summary.setText("总计￥"+String.valueOf(orderCommitAdapter.getMoney()));
        reduce.setText("优惠￥"+String.valueOf(orderCommitAdapter.getReduce()));
        total_money.setText(String.valueOf(orderCommitAdapter.getMoney()-orderCommitAdapter.getReduce()));
        finally_paid.setText(total_money.getText());
        finally_reduce.setText(reduce.getText());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderCommit=findViewById(R.id.order_commit);
        orderCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderCommitActivity.this, OrderInfActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("orderRecipes", (Serializable) shopCarRecipes);
                intent.putExtra("OrderCommitToOrderInf", args);
                startActivity(intent);
            }
        });
    }
}

class OrderCommitAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShopCarRecipe> shopCarRecipes;
    private Context context;
    private Double money;
    private Double reduce;

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

    public void resetReduce(Double money) {
        this.reduce = 0.0;
    }
}
