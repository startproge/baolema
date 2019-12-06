package com.example.baolema.ui.home;

import android.content.AbstractThreadedSyncAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.baolema.R;
import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderSum;
import com.example.baolema.bean.Orders;
import com.example.baolema.bean.ShopCarRecipe;
import com.example.baolema.controller.OrderController;
import com.example.baolema.ui.order.OrderInfActivity;
import com.example.baolema.util.httpUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderCommitActivity extends AppCompatActivity {
    private String urlStr = "http://47.98.229.17:8002/blm";
    private RecyclerView orderRecipesRecycleView;
    private ArrayList<ShopCarRecipe> shopCarRecipes;
    private ArrayList<OrderInf> orderInfs;
    private int orderId;
    private int shopId;
    private double reduce;
    private Button orderCommit;
    private TextView summary;
    private TextView reduceTextView;
    private TextView total_money;
    private TextView finally_paid;
    private EditText editRemark;
    private TextView finally_reduce;
    private OrderSum orderSum;
    private TextView userName;
    private TextView userTel;
    private TextView userLocation;
    private SharedPreferences pref;
    private Orders orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_commit);

        Toolbar toolbar = findViewById(R.id.tool_bar_shop_commit);
        Intent intent = getIntent();

        orders=new Orders();
        pref = getSharedPreferences("user", MODE_PRIVATE);
        orders.setUserId(pref.getInt("userId", -1));
        shopId=intent.getIntExtra("shopId",0);
        orders.setShopId(shopId);

        userName = findViewById(R.id.user_name);
        userName.setText("姓名: "+pref.getString("userName",""));
        userTel = findViewById(R.id.user_tel);
        userTel.setText("电话: "+pref.getString("userTel","  "));
        userLocation = findViewById(R.id.user_location);
        userLocation.setText("地址: "+pref.getString("userAddress",""));

        editRemark = findViewById(R.id.edt_remark);

        reduce=intent.getDoubleExtra("reduce",-1);
        Bundle args = intent.getBundleExtra("ShopCarToOrderCommit");
        shopCarRecipes = (ArrayList<ShopCarRecipe>) args.getSerializable("ShopCarRecipes");
        orderRecipesRecycleView = findViewById(R.id.recipe_recycleview);
        LinearLayoutManager orderLayoutManager = new LinearLayoutManager(this);
        orderRecipesRecycleView.setLayoutManager(orderLayoutManager);
        OrderCommitAdapter orderCommitAdapter = new OrderCommitAdapter(shopCarRecipes, this);
        orderCommitAdapter.resetMoney();
        orderRecipesRecycleView.setAdapter(orderCommitAdapter);
        summary=findViewById(R.id.total_money);
        reduceTextView=findViewById(R.id.reduce_money);
        total_money=findViewById(R.id.total_paid_money);
        finally_paid=findViewById(R.id.finally_pay_money);
        finally_reduce=findViewById(R.id.finally_reduce_money);
        orderCommitAdapter.resetMoney();

        summary.setText("总计￥"+String.valueOf(orderCommitAdapter.getMoney()));
        reduceTextView.setText("优惠￥"+String.valueOf(reduce));
        total_money.setText(String.valueOf(orderCommitAdapter.getMoney()-reduce));
        finally_paid.setText(total_money.getText());
        finally_reduce.setText(String.valueOf(reduce));
        toolbar.setNavigationOnClickListener(v -> finish());

        orderCommit=findViewById(R.id.order_commit);
        orderCommit.setOnClickListener(v -> {
            //Intent intent=new Intent(OrderCommitActivity.this, OrderInfActivity.class);
            orders.setOrderRemark(editRemark.getText().toString());
            //addOrderByHttp(orders);
            try {
                ThreadAddOrder thread1=new ThreadAddOrder();
                thread1.start();
                thread1.join();
                for(int i=0;i<shopCarRecipes.size();i++){
                    ShopCarRecipe shopCarRecipe=shopCarRecipes.get(i);
                    ThreadAddOrderInf thread2=new ThreadAddOrderInf(shopCarRecipe.getRecipeId(),i+1,
                            shopCarRecipe.getNum());
                    thread2.start();
                    thread2.join();
                }
                ThreadgetOrderSum thread3=new ThreadgetOrderSum();
                thread3.start();
                thread3.join();
            }catch (Exception e){

            }

            //getOrderSumByHttp();
            //intent.putExtra("orderId",orderId);
            //startActivity(intent);
            //finish();
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("addOrders", "handleMessage: " );
                    break;
                case 3:
                    Log.d("getOrderSum", "handleMessage: " );
                    Intent back = new Intent();
                    back.putExtra("isCommit", true);
                    OrderCommitActivity.this.setResult(RESULT_OK,back);

                    Intent intent=new Intent(OrderCommitActivity.this, OrderInfActivity.class);
                    intent.putExtra("orderSum", orderSum);
                    startActivity(intent);

                    OrderCommitActivity.this.finish();
                    break;
                    //finish();
                case 2:
                    Log.d("case3", "handleMessage: " );

                    break;
                default:
                    break;
            }
        }
    };

    private class  ThreadAddOrder extends Thread{
        @Override
        public void run() {
            orderId= JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Order/addOrders?shopId=" +orders.getShopId() +"&userId="+orders.getUserId()+"&orderRemark="+orders.getOrderRemark()), Integer.class);
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    private class  ThreadAddOrderInf extends Thread{
        private int recipeId;
        private int listId;
        private int num;
        public  ThreadAddOrderInf(int recipeId,int listId,int num){
            this.recipeId=recipeId;
            this.listId=listId;
            this.num=num;
        }
        @Override
        public void run() {
            JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/OrderInf/addOrderInf?recipe_id=" + recipeId
                    +"&order_id="+orderId+"&list_id="+listId+"&order_recipe_number="+num), Integer.class);
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }


    }
    private class  ThreadgetOrderSum extends Thread{
        @Override
        public void run() {
            orderSum=new OrderController().getOrderSumById(orderId);
            Log.d("CommitStatus",orderSum.getOrderStatus());
            Message message = new Message();
            message.what = 3;
            handler.sendMessage(message);
        }

    }
}

class OrderCommitAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShopCarRecipe> shopCarRecipes;
    private Context context;
    private Double money;

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
        mholder.num.setText("×"+shopCarRecipe.getNum());

    }

    @Override
    public int getItemCount() {
        return shopCarRecipes.size();
    }

    public Double getMoney() {
        return money;
    }

    public void resetMoney(){
        this.money=0.0;
        for(int i=0;i<shopCarRecipes.size();i++)
            this.money+=shopCarRecipes.get(i).getMoney()*shopCarRecipes.get(i).getNum();
    }

}
