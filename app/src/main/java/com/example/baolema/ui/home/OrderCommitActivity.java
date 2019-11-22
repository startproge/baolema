package com.example.baolema.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.ShopCarRecipe;

import java.util.ArrayList;

public class OrderCommitActivity extends AppCompatActivity{
    private RecyclerView orderRecipesRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_commit);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<ShopCarRecipe> shopCarRecipes = (ArrayList<ShopCarRecipe>) args.getSerializable("orderRecipes");
        orderRecipesRecycleView=findViewById(R.id.recipe_recycleview);
        LinearLayoutManager orderLayoutManager=new LinearLayoutManager(this);
        orderRecipesRecycleView.setLayoutManager(orderLayoutManager);
        final ShopCarAdapter shopCarAdapter=new ShopCarAdapter(shopCarRecipes,this);
        orderRecipesRecycleView.setAdapter(shopCarAdapter);

    }
}
