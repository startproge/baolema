package com.example.baolema.bean;

import java.io.Serializable;

public class ShopCarRecipe implements Serializable {
    int recipeId;
    String name;
    Double money;
    int num;

    public  ShopCarRecipe(){

    }
    public ShopCarRecipe(int recipeId,String name,Double money,int num){
        this.recipeId=recipeId;
        this.name=name;
        this.money=money;
        this.num=num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
