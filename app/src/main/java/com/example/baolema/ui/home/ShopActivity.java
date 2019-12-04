package com.example.baolema.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.baolema.MainActivity;
import com.example.baolema.R;
import com.example.baolema.bean.Recipe;
import com.example.baolema.bean.ShopCarRecipe;
import com.example.baolema.util.httpUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import im.unicolas.trollbadgeview.LabelView;


import static com.example.baolema.ui.home.RecipeAdapter.*;

public class ShopActivity extends AppCompatActivity {
    private int shopId;
    private String urlStr = "http://47.98.229.17:8002/blm";
    private RecyclerView recipeRecycleView;
    private RecipeAdapter recipeAdapter;
    private ArrayList<ShopCarRecipe> shopCarRecipes = new ArrayList<>();
    private List<Recipe> recipeList;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ConstraintLayout shopCarInf;
    private Button settlement_fee;
    private RecyclerView shoppingCarRecycleview;
    private ShopCarAdapter shopCarAdapter;
    private LabelView labelView;
    private TextView money;
    private TextView reduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);
        Toolbar toolbar = findViewById(R.id.tool_bar_shop);
        money=findViewById(R.id.order_money);
        reduce=findViewById(R.id.reduce_money);
        Intent intent = getIntent();
        toolbar.setTitle(intent.getStringExtra("shopName"));
        shopId = intent.getIntExtra("shopId", 0);
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("点菜").setContent(R.id.tab_order));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("商家").setContent(R.id.tab_shop_scrollview));
        //getShopRecipeListByHttp();
        //购物车RecycleView
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        LinearLayoutManager shopLayoutManager = new LinearLayoutManager(this);
        shoppingCarRecycleview = findViewById(R.id.shopping_car_recycleview);
        shoppingCarRecycleview.setLayoutManager(shopLayoutManager);
        shopCarAdapter = new ShopCarAdapter(shopCarRecipes, this);
        shoppingCarRecycleview.setAdapter(shopCarAdapter);
        shopCarAdapter.OnRecycleItemClickListener(position -> {
            shopCarAdapter.notifyDataSetChanged();
            money.setText(String.valueOf(shopCarAdapter.getMoney()));
            reduce.setText(String.valueOf(shopCarAdapter.getReduce()));
        });

        //商家菜单RecycleView
        recipeRecycleView = findViewById(R.id.recipe_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recipeRecycleView.setLayoutManager(layoutManager);
        //final RecipeAdapter recipeAdapter = new RecipeAdapter();
        recipeList = new ArrayList<>();

//        recipeAdapter.OnRecycleItemClickListener(new RecipeAdapter.OnRecycleItemClickListener() {
//            @Override
//            public void OnRecycleItemClickListener(int position) {
//                boolean isExist = true;
//                for (int i = 0; i < shopCarAdapter.getShopCarRecipes().size(); i++)
//                    if (recipeList.get(position).getRecipeName().equals(shopCarAdapter.getShopCarRecipes().get(i).getName())) {
//                        int num = shopCarAdapter.getShopCarRecipes().get(i).getNum();
//                        shopCarAdapter.getShopCarRecipes().get(i).setNum(++num);
//                        isExist = false;
//                        break;
//                    }
//                if (isExist) {
//                    ShopCarRecipe shopCarRecipe = new ShopCarRecipe(recipeList.get(position).getRecipeName()
//                            , recipeList.get(position).getRecipePrice(), 1);
//                    shopCarAdapter.getShopCarRecipes().add(shopCarRecipe);
//                }
//                shopCarAdapter.notifyDataSetChanged();
//                shopCarAdapter.resetMoney();
//                shopCarAdapter.resetReduce(shopCarAdapter.getMoney());
//                money.setText(String.valueOf(shopCarAdapter.getMoney()));
//                reduce.setText(String.valueOf(shopCarAdapter.getReduce()));
//            }
//
//        });

        //recipeRecycleView.setAdapter(recipeAdapter);
//        getShopRecipeListByHttp();

        labelView = findViewById(R.id.shopping_car_icon);
        labelView.setLabelMode(LabelView.LABEL_MODE_IMG);
        //设置角标是否显示
        labelView.setLabelViewVisiable(true);
        //设置角标内字符  设置为null 为只显示小红点不显示内字符
        labelView.setLabelNum("7");
        //设置角标的背景颜色   default : 0xffef4836
        labelView.setLabelBg(0xffef4836);
        //返回角标是否显示  default : false
        labelView.setBitmap4Icon(R.mipmap.ic_shopping_car);
        boolean labelViewVisiable = labelView.isLabelViewVisiable();
        //返回角标内的字符
        String labelNum = labelView.getLabelNum();
        //返回角标依附的文字
        String word = labelView.getWord();
        labelView.setOnClickListener(v -> {
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        //弹出购物车
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState != BottomSheetBehavior.STATE_DRAGGING) {
                    ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                    if (bottomSheet.getHeight() > 600) {
                        layoutParams.height = 600;
                        bottomSheet.setLayoutParams(layoutParams);
                    }
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        //提交订单
        settlement_fee = findViewById(R.id.settlement_fee);
        settlement_fee.setOnClickListener(v -> {
            Intent intent1 = new Intent(ShopActivity.this, OrderCommitActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ShopCarRecipes", (Serializable) shopCarAdapter.getShopCarRecipes());
            intent1.putExtra("ShopCarToOrderCommit", args);
            startActivity(intent1);
        });

        toolbar.setNavigationOnClickListener(v -> {
            //点击返回主页,对购物车进行操作
            finish();
        });

        Thread thread=new Thread(()->{
            recipeList = JSON.parseArray(httpUtil.getHttpInterface(urlStr + "/Recipe/getRecipeList?shopId=" + shopId), Recipe.class);
            Log.d("activity123", String.valueOf(recipeList.size()));
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
        }

        recipeAdapter = new RecipeAdapter(recipeList, ShopActivity.this);
        recipeRecycleView.setAdapter(recipeAdapter);
        recipeAdapter.OnRecycleItemClickListener(position -> {
            boolean isExist = true;
            for (int i = 0; i < shopCarAdapter.getShopCarRecipes().size(); i++)
                if (recipeList.get(position).getRecipeName().equals(shopCarAdapter.getShopCarRecipes().get(i).getName())) {
                    int num = shopCarAdapter.getShopCarRecipes().get(i).getNum();
                    shopCarAdapter.getShopCarRecipes().get(i).setNum(++num);
                    isExist = false;
                    break;
                }
            if (isExist) {
                ShopCarRecipe shopCarRecipe = new ShopCarRecipe(recipeList.get(position).getRecipeId(),recipeList.get(position).getRecipeName()
                        , recipeList.get(position).getRecipePrice(), 1);
                shopCarAdapter.getShopCarRecipes().add(shopCarRecipe);
            }
            shopCarAdapter.notifyDataSetChanged();
            shopCarAdapter.resetMoney();
            shopCarAdapter.resetReduce(shopCarAdapter.getMoney());
            money.setText(String.valueOf(shopCarAdapter.getMoney()));
            reduce.setText(String.valueOf(shopCarAdapter.getReduce()));
        });
        for (Recipe r : recipeList)
            getRecipeByHttp(r.getRecipeId());
    }

//    void getShopRecipeListByHttp() {
//        new Thread(() -> {
//            recipeList = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Recipe/getRecipeList?shopId=" + shopId),
//                    new TypeReference<List<Recipe>>() {
//                    });
//            Log.d("activity123", String.valueOf(recipeList.size()));
//            Message message = new Message();
//            message.what = 1;
//            handler.sendMessage(message);
//            Log.e("ShopActivity", String.valueOf(recipes.size()));
//
//        }).start();
//    }

    void getRecipeByHttp(int recipeId) {
        new Thread(() -> {
            Recipe recipe= JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Recipe/getRecipe?recipeId=" + recipeId), Recipe.class);
            for (int i = 0; i < recipeList.size(); i++)
                if (recipeList.get(i).getRecipeId() == recipeId)
                    recipeList.get(i).setRecipeImage(recipe.getRecipeImage());
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    recipeAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

}


