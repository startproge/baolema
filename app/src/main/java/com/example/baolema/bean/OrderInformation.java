package com.example.baolema.bean;

public class OrderInformation {
    private int orderInfId;
    private int recipeId;
    private int ordersId;
    private int listId;
    private int orderRecipeNumber;
    private double orderDiscount;
    private String recipeStatus;
    private double recipePrice;
    private String recipeName;

    public int getOrderInfId() {
        return orderInfId;
    }

    public void setOrderInfId(int orderInfId) {
        this.orderInfId = orderInfId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getOrderRecipeNumber() {
        return orderRecipeNumber;
    }

    public void setOrderRecipeNumber(int orderRecipeNumber) {
        this.orderRecipeNumber = orderRecipeNumber;
    }

    public double getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(double orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public String getRecipeStatus() {
        return recipeStatus;
    }

    public void setRecipeStatus(String recipeStatus) {
        this.recipeStatus = recipeStatus;
    }

    public double getRecipePrice() {
        return recipePrice;
    }

    public void setRecipePrice(double recipePrice) {
        this.recipePrice = recipePrice;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
