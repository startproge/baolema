package com.example.baolema.DAO;

import com.example.baolema.bean.Recipe;
import com.example.baolema.bean.Shop;

import java.util.List;

public interface ShopDao {

    // url/Recipe/getRecipeList?shopId=
    List<Integer> getRecipeIdList(int shopId);
}
