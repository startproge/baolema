package com.example.baolema.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.baolema.R;
import com.example.baolema.bean.Activity;
import com.example.baolema.bean.Recipe;
import com.example.baolema.bean.Shop;
import com.example.baolema.bean.ShopCarRecipe;
import com.example.baolema.bean.ShopEva;
import com.example.baolema.controller.ActivityController;
import com.example.baolema.controller.MyDBHelperController;
import com.example.baolema.controller.ShopController;
import com.example.baolema.sqlite.MyShopDBHelper;
import com.example.baolema.util.httpUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.flipboard.bottomsheet.BottomSheetLayout;

import im.unicolas.trollbadgeview.LabelView;

public class ShopActivity extends AppCompatActivity {
    private MyShopDBHelper dbHelper;
    private SQLiteDatabase db;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Shop shop;
    private int shopId;
    private int userId;
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
    private ImageView labelView;
    private TextView money;
    private TextView reduce;
    private Button clear_shopping_car;
    private List<Activity> activitys;
    private BottomSheetLayout bottomSheetLayout;
    private TextView text_shop_phone;
    private TextView text_shop_location;
    private TextView shop_board;
    private RecyclerView shop_evaluate_recyclerview;
    private List<ShopEva> shopEvas;
    CommentAdapter commentAdapter;

    private TextView activityText;
    private int isCommit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);
        dbHelper = new MyShopDBHelper(this, "ShopCarList.db", null, 2);
        db = dbHelper.getWritableDatabase();
        pref = getSharedPreferences("user", MODE_PRIVATE);
        editor = pref.edit();

        userId = pref.getInt("userId", 0);
        Toolbar toolbar = findViewById(R.id.tool_bar_shop);
        money = findViewById(R.id.order_money);
        reduce = findViewById(R.id.reduce_money);
        activityText = findViewById(R.id.preferential_text);

        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("点菜").setContent(R.id.tab_order));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("商家").setContent(R.id.tab_shop_scrollview));


        text_shop_phone = findViewById(R.id.text_shop_phone);
        text_shop_location = findViewById(R.id.text_shop_location);
        shop_board = findViewById(R.id.shop_board);
        Intent intent = getIntent();
        shop = (Shop) intent.getSerializableExtra("shop");
        if (shop != null) {
            toolbar.setTitle(shop.getShopName());
            shopId = shop.getShopId();
            text_shop_phone.setText(shop.getShopTel());
            text_shop_location.setText(shop.getShopAddress());
            shop_board.setText(shop.getShopNotice());
        }
        //getShopRecipeListByHttp();

        //购物车RecycleView

        bottomSheetLayout=findViewById(R.id.bottom_sheet_layout);
        View shopcarView=LayoutInflater.from(this).inflate(R.layout.activity_shopcar_layout,null);

        //mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        LinearLayoutManager shopLayoutManager = new LinearLayoutManager(this);

        //shoppingCarRecycleview = findViewById(R.id.shopping_car_recycleview);
        shoppingCarRecycleview=shopcarView.findViewById(R.id.shopping_car_recycleview);
        clear_shopping_car=shopcarView.findViewById(R.id.clear_shopping_car);

        shoppingCarRecycleview.setLayoutManager(shopLayoutManager);

        shopCarRecipes = new MyDBHelperController().getShopCars(db, shopId, userId);
        shopCarAdapter = new ShopCarAdapter(shopCarRecipes, this);

        try {
            ThreadgetActivity threadgetActivity = new ThreadgetActivity();
            threadgetActivity.start();
            threadgetActivity.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        shoppingCarRecycleview.setAdapter(shopCarAdapter);
        shopCarAdapter.OnRecycleItemClickListener(position -> {
            shopCarAdapter.notifyDataSetChanged();
            money.setText(String.valueOf(shopCarAdapter.getMoney()));
            reduce.setText("优惠"+String.valueOf(shopCarAdapter.getReduce()));
        });

        clear_shopping_car.setOnClickListener(v -> {
            bottomSheetLayout.dismissSheet();
            shopCarAdapter.getShopCarRecipes().clear();
            shopCarAdapter.resetMoney();
            shopCarAdapter.resetReduce();
            shopCarAdapter.notifyDataSetChanged();
            money.setText(String.valueOf(shopCarAdapter.getMoney()));
            reduce.setText("优惠"+String.valueOf(shopCarAdapter.getReduce()));

        });

        //商家菜单RecycleView
        recipeRecycleView = findViewById(R.id.recipe_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recipeRecycleView.setLayoutManager(layoutManager);
        recipeList = new ArrayList<>();


        labelView = findViewById(R.id.shopping_car_icon);
        /*labelView.setLabelMode(LabelView.LABEL_MODE_IMG);
        //设置角标是否显示
        labelView.setLabelViewVisiable(true);
        //设置角标内字符  设置为null 为只显示小红点不显示内字符
        labelView.setLabelNum("7");
        //设置角标的背景颜色   default : 0xffef4836
        labelView.setLabelBg(0xffef4836);
        //返回角标是否显示  default : false*/
        //labelView.setBitmap4Icon(R.mipmap.ic_shopping_car);
        labelView.setImageResource(R.mipmap.ic_shopping_car);
        /*boolean labelViewVisiable = labelView.isLabelViewVisiable();
        //返回角标内的字符
        String labelNum = labelView.getLabelNum();
        //返回角标依附的文字
        String word = labelView.getWord();*/

//      labelView.setOnClickListener(v -> {
//            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            }
//        });
        //弹出购物车
        labelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bottomSheetLayout.isSheetShowing())
                    bottomSheetLayout.showWithSheetView(shopcarView);
                else
                    bottomSheetLayout.dismissSheet();
            }
        });

        //弹出购物车
//        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (newState != BottomSheetBehavior.STATE_DRAGGING) {
//                    ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
//                    if (bottomSheet.getHeight() > 600) {
//                        layoutParams.height = 600;
//                        bottomSheet.setLayoutParams(layoutParams);
//                    }
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//            }
//        });

        //提交订单
        settlement_fee = findViewById(R.id.settlement_fee);
        settlement_fee.setOnClickListener(v -> {
            if(shopCarAdapter.getShopCarRecipes().size()==0) {
                new AlertDialog.Builder(this).setTitle("提示")
                        .setMessage("请添加商品")
                        .setPositiveButton("确定", null)
                        .show();
            }
            else if(userId==0){
                new AlertDialog.Builder(this).setTitle("提示")
                        .setMessage("请登陆")
                        .setPositiveButton("确定", null)
                        .show();
            }
            else {
                Intent intent1 = new Intent(ShopActivity.this, OrderCommitActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ShopCarRecipes", (Serializable) shopCarAdapter.getShopCarRecipes());
                intent1.putExtra("ShopCarToOrderCommit", args);
                intent1.putExtra("shopId", shopId);
                intent1.putExtra("reduce", shopCarAdapter.getReduce());
                startActivityForResult(intent1, 1);
            }
        });

        toolbar.setNavigationOnClickListener(v -> {
            //点击返回主页,对购物车进行操作
            finish();
        });

        Thread thread = new Thread(() -> {
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
                ShopCarRecipe shopCarRecipe = new ShopCarRecipe(recipeList.get(position).getRecipeId(), recipeList.get(position).getRecipeName()
                        , recipeList.get(position).getRecipePrice(), 1);
                shopCarAdapter.getShopCarRecipes().add(shopCarRecipe);
            }
            shopCarAdapter.notifyDataSetChanged();
            shopCarAdapter.resetMoney();
            shopCarAdapter.resetReduce();
            money.setText(String.valueOf(shopCarAdapter.getMoney()));
            reduce.setText("优惠"+String.valueOf(shopCarAdapter.getReduce()));
        });

        for (Recipe r : recipeList)
            getRecipeByHttp(r.getRecipeId());

        shop_evaluate_recyclerview = findViewById(R.id.shop_evaluate_recyclerview);
        LinearLayoutManager ShopEvalayoutManager = new LinearLayoutManager(this);
        shop_evaluate_recyclerview.setLayoutManager(ShopEvalayoutManager);
        commentAdapter = new CommentAdapter(this);
        shop_evaluate_recyclerview.setAdapter(commentAdapter);
        getShopEvaByHttp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    isCommit = data.getIntExtra("isCommit", 0);
                    if (isCommit == 0) {
                        shopCarAdapter.getShopCarRecipes().clear();
                        shopCarAdapter.resetMoney();
                        shopCarAdapter.resetReduce();
                        shopCarAdapter.notifyDataSetChanged();
                        new MyDBHelperController().deleteShopCar(db, shopId, userId);
                        finish();
                    }
                    else{
                        try {
                            Thread thread = new Thread(() -> {
                                recipeList = JSON.parseArray(httpUtil.getHttpInterface(urlStr + "/Recipe/getRecipeList?shopId=" + shopId), Recipe.class);
                                Log.d("activity123", String.valueOf(recipeList.size()));
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            });
                            thread.start();
                            thread.join();
                            Log.d("Tangyuan",recipeList.get(recipeList.size()-1).getRecipeName());
                            for (Recipe r : recipeList)
                                getRecipeByHttp(r.getRecipeId());
                            recipeAdapter.setRecipes(recipeList);

                            recipeAdapter.notifyDataSetChanged();
                        }catch (Exception e){

                        }

                        String name= shopCarAdapter.getRecipeName(isCommit);
                        if(name!=null) {
                            new AlertDialog.Builder(this).setTitle("提示")
                                    .setMessage(name+"库存不足")
                                    .setPositiveButton("确定", null)
                                    .show();
                        }
                        int potision=0;
                        shopCarAdapter.deleteRecipe(isCommit);
                        shopCarAdapter.resetMoney();
                        shopCarAdapter.resetReduce();
                        shopCarAdapter.notifyDataSetChanged();
                        new MyDBHelperController().deleteShopCarRecipe(db, shopId, userId,isCommit);
                        money.setText(String.valueOf(shopCarAdapter.getMoney()));
                        reduce.setText("优惠"+String.valueOf(shopCarAdapter.getReduce()));
                    }
                }
                break;
        }

    }


    void getRecipeByHttp(int recipeId) {
        new Thread(() -> {
            Recipe recipe = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Recipe/getRecipe?recipeId=" + recipeId), Recipe.class);
            for (int i = 0; i < recipeList.size(); i++)
                if (recipeList.get(i).getRecipeId() == recipeId)
                    recipeList.get(i).setRecipeImage(recipe.getRecipeImage());
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }).start();
    }

   /* void getActivityByHttp() {
        new Thread(() -> {
            activitys=new ActivityController().getActivitiesByShopId(shopId);
            Message message = new Message();
            message.what = 3;
            handler.sendMessage(message);
        }).start();
    }*/

    private class ThreadgetActivity extends Thread {
        @Override
        public void run() {
            activitys = new ActivityController().getActivitiesByShopId(shopId);
            Message message = new Message();
            message.what = 3;
            handler.sendMessage(message);
        }
    }

    void getShopEvaByHttp() {
        new Thread(() -> {
            shopEvas = new ShopController().getShopEva(shopId);
            for (ShopEva e :
                    shopEvas) {
                Log.e("a", "getShopEvaByHttp: "+e.getShopEvaluateContent() );
            }
            if (shopEvas != null) {
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);
            }
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
                case 3:
                    Log.d("ActivitySize", String.valueOf(activitys.size()));
                    String text = "";
                    for (int i = 0; i < activitys.size(); i++) {
                        text += "满" + (int) activitys.get(i).getFullMoney() + "减" + (int) activitys.get(i).getReduceMoney();
                        //shopCarAdapter.getActivities().add(activitys.get(i));
                    }
                    activityText.setText(text);
                    shopCarAdapter.setActivities(activitys);
                    Log.d("reduceMoney", String.valueOf(shopCarAdapter.getActivities().size()));
                    shopCarAdapter.resetMoney();
                    shopCarAdapter.resetReduce();
                    shopCarAdapter.notifyDataSetChanged();
                    recipeAdapter.notifyDataSetChanged();
                    Log.d("reduceMoney", String.valueOf(shopCarAdapter.getReduce()));
                    money.setText(String.valueOf(shopCarAdapter.getMoney()));
                    reduce.setText("优惠"+String.valueOf(shopCarAdapter.getReduce()));
                    break;
                case 4:
                    commentAdapter.setShopEvaList(shopEvas);
                    //Log.d("shopEva",String.valueOf(commentAdapter.getShopEvaList().size()));
                    commentAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        new MyDBHelperController().deleteShopCar(db, shopId, userId);
        if (shopCarAdapter.getShopCarRecipes().size() != 0) {
            new MyDBHelperController().addShopCar(db, shopId, userId, shopCarAdapter.getShopCarRecipes());
        }
        Log.d("MyDBhelper", "保存成功" + shopCarAdapter.getShopCarRecipes().size());
        super.onDestroy();
    }
}

class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShopEva> shopEvaList;
    private Context context;

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView headImage;
        TextView name;
        TextView evaluate;
        TextView content;
        ImageView contentImage;

        public CommentViewHolder(View view) {
            super(view);
            headImage = view.findViewById(R.id.headImage);
            name = view.findViewById(R.id.name);
            evaluate = view.findViewById(R.id.evaluate);
            content = view.findViewById(R.id.content);
            contentImage = view.findViewById(R.id.contentImage);
        }
    }

    public CommentAdapter(Context context) {
        this.context = context;
        shopEvaList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_comment, parent, false);
        CommentViewHolder holder = new CommentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShopEva shopEva = shopEvaList.get(position);
        CommentViewHolder mholder = (CommentViewHolder) holder;
        mholder.headImage.setImageBitmap(BitmapFactory.decodeByteArray(shopEva.getUserImage(), 0, shopEva.getUserImage().length));
        mholder.name.setText(shopEva.getUserName());
        mholder.evaluate.setText(String.valueOf(shopEva.getShopGrade()));
        mholder.content.setText(shopEva.getShopEvaluateContent());
        if (!(shopEva.getShopEvaluateImage() == null || shopEva.getShopEvaluateImage().length == 0)) {
            mholder.contentImage.setImageBitmap(BitmapFactory.decodeByteArray(shopEva.getShopEvaluateImage(), 0, shopEva.getShopEvaluateImage().length));
            mholder.contentImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return shopEvaList.size();
    }

    public List<ShopEva> getShopEvaList() {
        return shopEvaList;
    }

    public void setShopEvaList(List<ShopEva> shopEvaList) {
        this.shopEvaList = shopEvaList;
    }
}

