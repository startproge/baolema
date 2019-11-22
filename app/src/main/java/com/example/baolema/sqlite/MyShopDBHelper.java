package com.example.baolema.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.baolema.bean.ShopCarRecipe;

import java.util.ArrayList;

public class MyShopDBHelper extends SQLiteOpenHelper {
    private static final String CREATE_SHOPCAR = "Create table ShopCar("
            + "id integer primary key autoincrement,"
            + "shopId integer,"
            + "name text,"
            + "money real,"
            + "num integer)";

    private Context context;

    public MyShopDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOPCAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void addShopCar(SQLiteDatabase db, int shopId, ArrayList<ShopCarRecipe> shopCarRecipes){

        for (ShopCarRecipe recipe: shopCarRecipes) {
            ContentValues values = new ContentValues();
            values.put("shopId", shopId);
            values.put("name", recipe.getName());
            values.put("money", recipe.getMoney());
            values.put("num", recipe.getNum());

            db.insert("ShopCar", null, values);
        }

    }

    public ArrayList<ShopCarRecipe> getShopCars(SQLiteDatabase db, int shopId){
        ArrayList<ShopCarRecipe> recipes = new ArrayList<>();

        Cursor cursor = db.query("shopId", new String[]{"id, shopId, name, money, num"},
               "shopId=?", new String[]{shopId+""}, null, null, null, null);
        if (cursor.moveToFirst()) {
            ShopCarRecipe recipe = new ShopCarRecipe();
            do {
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
                recipe.setNum(cursor.getInt(cursor.getColumnIndex("num")));
                recipes.add(recipe);
            } while (cursor.moveToNext());
        }
        return recipes;
    }

    public void deleteShopCar(){

    }

}
