package com.example.baolema.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.baolema.bean.ShopCarRecipe;

import java.util.ArrayList;

public class MyDBHelperController {

    public void addShopCar(SQLiteDatabase db, int shopId,int userId, ArrayList<ShopCarRecipe> shopCarRecipes){
        ContentValues values = new ContentValues();
        for (ShopCarRecipe recipe: shopCarRecipes) {
            values.put("userId",userId);
            values.put("shopId", shopId);
            values.put("recipeId",recipe.getRecipeId());
            values.put("name", recipe.getName());
            //Log.d("lastnumber",recipe.getName());
            values.put("money", recipe.getMoney());
            values.put("num", recipe.getNum());
            db.insert("ShopCar", null, values);
            values.clear();
        }
    }

    public ArrayList<ShopCarRecipe> getShopCars(SQLiteDatabase db, int shopId,int userId){
        ArrayList<ShopCarRecipe> recipes = new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from ShopCar where userId=? and shopId=?",new String[]{String.valueOf(userId),String.valueOf(shopId)});
        if (cursor.moveToFirst()) {
            do {
                ShopCarRecipe recipe = new ShopCarRecipe();
                recipe.setRecipeId(cursor.getInt(cursor.getColumnIndex("recipeId")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
                recipe.setNum(cursor.getInt(cursor.getColumnIndex("num")));
                recipes.add(recipe);
                for(ShopCarRecipe shopCarRecipe:recipes){
                    Log.d("insertdebug",shopCarRecipe.getName()+" "+String.valueOf(shopCarRecipe.getMoney())+" "+String.valueOf(shopCarRecipe.getNum()));
                }
            } while (cursor.moveToNext());
        }

        for(ShopCarRecipe shopCarRecipe:recipes){
            Log.d("lastnumber",shopCarRecipe.getName()+" "+String.valueOf(shopCarRecipe.getMoney())+" "+String.valueOf(shopCarRecipe.getNum()));
        }
        return recipes;
    }

    public void deleteShopCar(SQLiteDatabase db, int shopId,int userId){
        db.execSQL("delete from ShopCar where userId=? and shopId=?",new String[]{String.valueOf(userId),String.valueOf(shopId)});
    }

    public void deleteShopCarRecipe(SQLiteDatabase db, int shopId,int userId,int recipeId){
        db.execSQL("delete from ShopCar where userId=? and shopId=? and recipeId=?",new String[]{String.valueOf(userId),String.valueOf(shopId),
                String.valueOf(recipeId)});
    }
}
