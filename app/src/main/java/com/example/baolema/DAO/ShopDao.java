package com.example.baolema.DAO;

import com.example.baolema.bean.Recipe;
import com.example.baolema.bean.Shop;

import java.util.List;

public interface ShopDao {
    // url/Shop/getShopList
    List<Shop> getShopList();

    // url/Recipe/getRecipeList?shopId=
    List<Recipe> getRecipeList(int shopId);
}
