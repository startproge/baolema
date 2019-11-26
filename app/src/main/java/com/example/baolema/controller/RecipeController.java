package com.example.baolema.controller;

import com.alibaba.fastjson.JSON;
import com.example.baolema.DAO.RecipeDao;
import com.example.baolema.bean.Recipe;
import com.example.baolema.bean.Shop;
import com.example.baolema.util.httpUtil;

import java.util.List;

public class RecipeController implements RecipeDao {

    @Override
    public Recipe getRecipe(int RecipeId) {
        String path="http://47.98.229.17:8002/blm/Recipe/getRecipe?recipeId="+RecipeId;
        String result= httpUtil.getHttpInterface(path);
        Recipe recipe= JSON.parseObject(result,Recipe.class);
        return  recipe;
    }
}
