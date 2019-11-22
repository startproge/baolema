package com.example.baolema.bean;


import java.util.ArrayList;
import java.util.List;

public class Shop {
    private int shopId;
    private String shopName;
    private String shopAddress;
    private String shopTel;
    private double shopScore;
    private String shopNotice;
    private String shopTrademark;
    private String shopStatus;
    private int shopMonthSale;
    private List<Recipe> recipeList = new ArrayList<>();

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopTel() {
        return shopTel;
    }

    public void setShopTel(String shopTel) {
        this.shopTel = shopTel;
    }

    public double getShopScore() {
        return shopScore;
    }

    public void setShopScore(double shopScore) {
        this.shopScore = shopScore;
    }

    public String getShopNotice() {
        return shopNotice;
    }

    public void setShopNotice(String shopNotice) {
        this.shopNotice = shopNotice;
    }

    public String getShopTrademark() {
        return shopTrademark;
    }

    public void setShopTrademark(String shopTrademark) {
        this.shopTrademark = shopTrademark;
    }

    public String getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(String shopStatus) {
        this.shopStatus = shopStatus;
    }

    public int getShopMonthSale() {
        return shopMonthSale;
    }

    public void setShopMonthSale(int shopMonthSale) {
        this.shopMonthSale = shopMonthSale;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }
}
