package com.example.baolema.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.customize.NumImageView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import im.unicolas.trollbadgeview.LabelView;

public class ShopActivity extends AppCompatActivity {
    private RecyclerView recipeRecycleView;
    private BottomSheetLayout bottomsheet;
    private LinearLayout bottomsheetLayout;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ConstraintLayout shopCarInf;
    private Button settlement_fee;
    private RecyclerView shoppingCareRecycleview;
    private LabelView labelView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);
        TabHost tabHost=findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("点菜").setContent(R.id.tab_order));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("商家").setContent(R.id.tab_shop_scrollview));

        recipeRecycleView=findViewById(R.id.recipe_recycleview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recipeRecycleView.setLayoutManager(layoutManager);
        recipeRecycleView.setAdapter(new RecipeAdapter());

        labelView=findViewById(R.id.shopping_car_icon);
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

        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        LinearLayoutManager shopLayoutManager=new LinearLayoutManager(this);
        shoppingCareRecycleview=findViewById(R.id.shopping_car_recycleview);
        shoppingCareRecycleview.setLayoutManager(shopLayoutManager);
        shoppingCareRecycleview.setAdapter(new RecipeAdapter());
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

//        shopCarInf=findViewById(R.id.shopping_car_layout);
//        LinearLayoutManager shopLayoutManager=new LinearLayoutManager(this);
//        shoppingCareRecycleview=findViewById(R.id.shopping_car_recycleview);
//        shoppingCareRecycleview.setLayoutManager(shopLayoutManager);
//        shoppingCareRecycleview.setAdapter(new RecipeAdapter());
//        labelView.setOnClickListener(new View.OnClickListener() {
//            private ScaleAnimation animation;
//            @Override
//
//            public void onClick(View v) {
//                if(shopCarInf.getVisibility()== View.GONE){
//                    animation=new ScaleAnimation(1,1,0,1
//                            , Animation.RELATIVE_TO_SELF,1f,Animation.RELATIVE_TO_SELF,1f);
//                    animation.setDuration(200);
//                    shopCarInf.setVisibility(View.VISIBLE);
//                    shopCarInf.startAnimation(animation);
//                }
//                else{
//                    animation=new ScaleAnimation(1,1,1,0
//                            , Animation.RELATIVE_TO_SELF,1f,Animation.RELATIVE_TO_SELF,1f);
//                    animation.setDuration(200);
//                    shopCarInf.setVisibility(View.GONE);
//                    shopCarInf.startAnimation(animation);
//                }
//            }
//        });

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState != BottomSheetBehavior.STATE_DRAGGING) {
                    ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                    if (bottomSheet.getHeight() > 600 ) {
                        layoutParams.height = 600 ;
                        bottomSheet.setLayoutParams(layoutParams);
                    }
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
    });
        settlement_fee=findViewById(R.id.settlement_fee);
        settlement_fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShopActivity.this,OrderCommitActivity.class);
                startActivity(intent);
            }
        });

    }
}
