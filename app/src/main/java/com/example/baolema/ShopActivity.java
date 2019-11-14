package com.example.baolema;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShopActivity extends AppCompatActivity {
    private RecyclerView recipeRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        recipeRecycleView.setLayoutManager(layoutManager);
//        recipeRecycleView=findViewById(R.id.recipe_recycleview);
//        recipeRecycleView.setAdapter(new RecipeAdapter());
    }
}
