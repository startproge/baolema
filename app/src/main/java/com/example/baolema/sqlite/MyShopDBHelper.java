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
            + "userId integer,"
            + "shopId integer,"
            + "recipeId integer,"
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

}
