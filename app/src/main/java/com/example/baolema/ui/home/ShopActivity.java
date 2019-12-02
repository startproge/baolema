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
    private List<Recipe> recipes;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ConstraintLayout shopCarInf;
    private Button settlement_fee;
    private RecyclerView shoppingCarRecycleview;
    private ShopCarAdapter shopCarAdapter;
    private LabelView labelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);
        Toolbar toolbar = findViewById(R.id.tool_bar_shop);
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

        //商家菜单RecycleView
        recipeRecycleView = findViewById(R.id.recipe_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recipeRecycleView.setLayoutManager(layoutManager);
        //final RecipeAdapter recipeAdapter = new RecipeAdapter();
        recipes = new ArrayList<Recipe>();
        recipeAdapter = new RecipeAdapter(recipes, this);
        recipeRecycleView.setAdapter(recipeAdapter);
        //添加购物车
        /*recipeAdapter.OnRecycleItemClickListener(new OnRecycleItemClickListener() {
            @Override
            public void OnRecycleItemClickListener(int position) {
                //shopCarRecipes.add(new ShopCarRecipe("红烧排骨"+position,20.0,1));
                boolean isExist = true;
                for (int i = 0; i < shopCarAdapter.getRecipes().size(); i++) {
                    if (shopCarAdapter.getRecipes().get(i).getName().equals("红烧排骨" + position)) {
                        int num = shopCarAdapter.getRecipes().get(i).getNum();
                        shopCarAdapter.getRecipes().get(i).setNum(++num);
                        isExist = false;
                        break;
                    }
                }
                if (isExist)
                    shopCarAdapter.getRecipes().add(new ShopCarRecipe("红烧排骨" + position, 20.0, 1));
                shoppingCarRecycleview.getAdapter().notifyDataSetChanged();
            }
        });*/

        recipeAdapter.OnRecycleItemClickListener(new RecipeAdapter.OnRecycleItemClickListener() {
            @Override
            public void OnRecycleItemClickListener(int position) {
                boolean isExist = true;
                for (int i = 0; i < shopCarAdapter.getShopCarRecipes().size(); i++)
                    if (recipes.get(position).getRecipeName().equals(shopCarAdapter.getShopCarRecipes().get(i).getName())) {
                        int num = shopCarAdapter.getShopCarRecipes().get(i).getNum();
                        shopCarAdapter.getShopCarRecipes().get(i).setNum(++num);
                        isExist = false;
                        break;
                    }
                if (isExist) {
                    ShopCarRecipe shopCarRecipe = new ShopCarRecipe(recipes.get(position).getRecipeName()
                            , recipes.get(position).getRecipePrice(), 1);
                    shopCarAdapter.getShopCarRecipes().add(shopCarRecipe);
                }
                shoppingCarRecycleview.getAdapter().notifyDataSetChanged();
            }

        });

        //recipeRecycleView.setAdapter(recipeAdapter);
        getShopRecipeListByHttp();

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
        labelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
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
        settlement_fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, OrderCommitActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ShopCarRecipes", (Serializable) shopCarAdapter.getShopCarRecipes());
                intent.putExtra("ShopCarToOrderCommit", args);
                startActivity(intent);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击返回主页,对购物车进行操作

                finish();
            }
        });

    }

    void getShopRecipeListByHttp() {
        new Thread(() -> {
            recipes = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Recipe/getRecipeList?shopId=" + shopId),
                    new TypeReference<List<Recipe>>() {
                    });
            Log.d("activity123", String.valueOf(recipes.size()));
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
            //Log.e("ShopActivity", String.valueOf(recipes.size()));

        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    /*recipeAdapter = new RecipeAdapter();
                    recipeRecycleView.setAdapter(recipeAdapter);
                    recipeAdapter.OnRecycleItemClickListener(new RecipeAdapter.OnRecycleItemClickListener() {
                        @Override
                        public void OnRecycleItemClickListener(int position) {
                            boolean isExist = true;
                            for (int i = 0; i < shopCarRecipes.size(); i++)
                                if (recipes.get(position).getRecipeName().equals(shopCarRecipes.get(i).getName())){
                                    int num = shopCarAdapter.getRecipes().get(i).getNum();
                                    shopCarAdapter.getRecipes().get(i).setNum(++num);
                                    isExist = false;
                                    break;
                                }
                            if (isExist)
                                shopCarAdapter.getRecipes().add(new ShopCarRecipe( recipes.get(position).getRecipeName()
                                    , recipes.get(position).getRecipePrice() , 1));
                                shoppingCarRecycleview.getAdapter().notifyDataSetChanged();
                        }

                    });*/
                    Log.d("activity12", String.valueOf(recipes.size()));
                    recipeAdapter.setRecipes(recipes);
                    recipeAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

}


