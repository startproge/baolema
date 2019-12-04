package com.example.baolema.ui.order;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.baolema.R;
import com.example.baolema.bean.OrderInf;
import com.example.baolema.bean.OrderSum;
import com.example.baolema.bean.ShopCarRecipe;
import com.example.baolema.util.httpUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderInfFragment extends Fragment {
    private String urlStr = "http://47.98.229.17:8002/blm";
    private RecyclerView recyclerOrderRecipe;
    private int orderId;
    private List<ShopCarRecipe> orderRecipes;
    private OrderInfAdapter orderinfAdapter;
    private List<OrderInf> orderInfRecipes;
    private TextView shopName;
    private TextView orderNumber;
    private TextView orderTimeDay;
    private TextView orderTimeSecond;
    private TextView order_summary;
    private TextView order_reduce;
    private TextView order_money;
    private OrderSum orderSum;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order_inf,container,false);
        Bundle bundle= OrderInfFragment.this.getArguments();
        //orderId=bundle.getInt("orderId");
        recyclerOrderRecipe=view.findViewById(R.id.order_recipe_recycleview);
        shopName=view.findViewById(R.id.shop_name);
        orderNumber=view.findViewById(R.id.order_number);
        orderTimeDay=view.findViewById(R.id.order_time_day);
        orderTimeSecond=view.findViewById(R.id.order_time_second);
        order_summary=view.findViewById(R.id.total_money);
        order_reduce=view.findViewById(R.id.order_reduce);
        order_money=view.findViewById(R.id.total_paid_money);
        recyclerOrderRecipe.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderRecipes=new ArrayList<>();
        orderinfAdapter= new OrderInfAdapter(orderRecipes, getActivity());

        orderSum=(OrderSum)bundle.getSerializable("orderSum");
        /*if(orderId==0){
            orderRecipes = (ArrayList<ShopCarRecipe>) bundle.getSerializable("orderRecipes");
            orderinfAdapter= new OrderInfAdapter(orderRecipes, getActivity());
            recyclerOrderRecipe.setAdapter(orderinfAdapter);
            order_summary.setText("总计￥"+String.valueOf(orderinfAdapter.getMoney()));
            order_reduce.setText("优惠￥"+String.valueOf(orderinfAdapter.getReduce()));
            order_money.setText("实付￥"+String.valueOf(orderinfAdapter.getMoney()-orderinfAdapter.getReduce()));
        } else{
            orderSum=(OrderSum)bundle.getSerializable("orderSum");
            shopName.setText( orderSum.getShopName());
            orderNumber.setText(orderSum.getTemporaryId());
            orderRecipes=new ArrayList<ShopCarRecipe>();
            getOrderInfRecipeListByHttp();
        }*/
        if(orderSum!=null){
            orderId=orderSum.getOrderId();
            shopName.setText(orderSum.getShopName());
            orderNumber.setText(orderSum.getTemporaryId());
            order_summary.setText("总计￥"+String.valueOf(orderSum.getOrdersum()));
            order_reduce.setText("优惠￥"+String.valueOf(0));
            order_money.setText("实付￥"+String.valueOf(orderSum.getOrdersum()-0));
        }
        return view;
    }

    void getOrderInfByHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                orderInfRecipes = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/OrderInf/getOrderInfList?orderId=" + orderId),
                        new TypeReference<List<OrderInf>>() {
                        });
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
                //Log.e("ShopActivity", String.valueOf(recipes.size()));

            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    for(int i=0;i<orderInfRecipes.size();i++){
                        OrderInf orderInf=orderInfRecipes.get(i);
                        //orderRecipes.add(new ShopCarRecipe());
                    }
                    Log.d("orderInf",String.valueOf(orderRecipes.size()));
                    //orderinfAdapter= new OrderInfAdapter(orderRecipes, getActivity());
                    //orderinfAdapter.getRecipes().add();
                    orderinfAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
}

class OrderInfAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShopCarRecipe> shopCarRecipes;
    private Context context;
    private Double money;
    private Double reduce;

    static class OrderInfViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView money;
        TextView num;

        public OrderInfViewHolder(View view){
            super(view);
            name=view.findViewById(R.id.recipe_name);
            money=view.findViewById(R.id.recipe_money);
            num=view.findViewById(R.id.recipe_num);
        }
    }

    public OrderInfAdapter (List<ShopCarRecipe> arrayList, Context context){
        if(arrayList.size()==0)
            shopCarRecipes=new ArrayList<>();
        else
            shopCarRecipes=arrayList;
        this.context=context;
        resetMoney();
        resetReduce(money);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_order,parent,false);
        OrderInfViewHolder holder=new OrderInfViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        OrderInfViewHolder mholder=(OrderInfViewHolder) holder;
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

    public void setShopCarRecipes(List<ShopCarRecipe> shopCarRecipes) {
        this.shopCarRecipes = shopCarRecipes;
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