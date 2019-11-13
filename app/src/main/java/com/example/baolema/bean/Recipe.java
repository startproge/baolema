package com.example.baolema.bean;


public class Recipe {
    private int recipeId;
    private int shopId;
    private String recipeName;
    private double recipePrice;
    private int monthlySale;
    private String recipeNotice;
    private String recipeImage;
    private int recipeRemain;
    private double recipeDiscount;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public double getRecipePrice() {
        return recipePrice;
    }

    public void setRecipePrice(double recipePrice) {
        this.recipePrice = recipePrice;
    }

    public int getMonthlySale() {
        return monthlySale;
    }

    public void setMonthlySale(int monthlySale) {
        this.monthlySale = monthlySale;
    }

    public String getRecipeNotice() {
        return recipeNotice;
    }

    public void setRecipeNotice(String recipeNotice) {
        this.recipeNotice = recipeNotice;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public int getRecipeRemain() {
        return recipeRemain;
    }

    public void setRecipeRemain(int recipeRemain) {
        this.recipeRemain = recipeRemain;
    }

    public double getRecipeDiscount() {
        return recipeDiscount;
    }

    public void setRecipeDiscount(double recipeDiscount) {
        this.recipeDiscount = recipeDiscount;
    }
}
