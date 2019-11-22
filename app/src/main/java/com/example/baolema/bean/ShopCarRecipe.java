package com.example.baolema.bean;

public class ShopCarRecipe {
    String name;
    Double money;
    int num;

    public  ShopCarRecipe(){

    }
    public ShopCarRecipe(String name,Double money,int num){
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

}
