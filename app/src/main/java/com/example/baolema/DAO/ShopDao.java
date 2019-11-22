package com.example.baolema.DAO;

import com.example.baolema.bean.Recipe;
import com.example.baolema.bean.Shop;

import java.util.List;

public interface ShopDao {
    // url?type=android&req=shopList
    List<Shop> getShopList();

    // url?type=android&req=recipeList&shopId=
    List<Recipe> getRecipeList(int shopId);
}
