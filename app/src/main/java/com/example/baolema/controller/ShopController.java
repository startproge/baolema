package com.example.baolema.controller;

import com.alibaba.fastjson.JSON;
import com.example.baolema.DAO.ShopDao;
import com.example.baolema.bean.Recipe;
import com.example.baolema.bean.Shop;
import com.example.baolema.util.httpUtil;

import java.util.List;

public class ShopController implements ShopDao {
//    @Override
//    public List<Shop> getShopList() {
//        String path="http://47.98.229.17:8002/blm/Shop/getShopList";
//        String result= httpUtil.getHttpInterface(path);
//        List<Shop> lst= JSON.parseArray(result,Shop.class);
//        return lst;
//    }
//
//    @Override
//    public List<Recipe> getRecipeList(int shopId) {
//        String path="http://47.98.229.17:8002/blm/Recipe/getRecipeList?shopId="+shopId;
//        String result= httpUtil.getHttpInterface(path);
//        List<Recipe> lst= JSON.parseArray(result,Recipe.class);
//        return lst;
//    }


    @Override
    public List<Integer> getRecipeIdList(int shopId) {
        String path="http://47.98.229.17:8002/blm/Recipe/getRecipeIdList?shopId="+shopId;
        String result= httpUtil.getHttpInterface(path);
        List<Integer> lst= JSON.parseArray(result,Integer.class);
        return lst;
    }
}
