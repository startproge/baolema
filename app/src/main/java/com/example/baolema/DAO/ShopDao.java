package com.example.baolema.DAO;

import com.example.baolema.bean.Recipe;

import java.util.ArrayList;

public interface ShopDao {
     ArrayList<Recipe> getRecipesByShopId(int shopId);
}
