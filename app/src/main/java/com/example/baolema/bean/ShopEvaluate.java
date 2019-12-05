package com.example.baolema.bean;

import java.util.List;

public class ShopEvaluate {
    private int shopEvaluateId;
    private int shopId;
    private int shopEvaluateOrder;
    private double shopGrade;
    private int userId;
    private String shopEvaluateContent;
    private byte shopEvaluateImage;

    public int getShopEvaluateId() {
        return shopEvaluateId;
    }

    public void setShopEvaluateId(int shopEvaluateId) {
        this.shopEvaluateId = shopEvaluateId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getShopEvaluateOrder() {
        return shopEvaluateOrder;
    }

    public void setShopEvaluateOrder(int shopEvaluateOrder) {
        this.shopEvaluateOrder = shopEvaluateOrder;
    }

    public double getShopGrade() {
        return shopGrade;
    }

    public void setShopGrade(double shopGrade) {
        this.shopGrade = shopGrade;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShopEvaluateContent() {
        return shopEvaluateContent;
    }

    public void setShopEvaluateContent(String shopEvaluateContent) {
        this.shopEvaluateContent = shopEvaluateContent;
    }

    public byte getShopEvaluateImage() {
        return shopEvaluateImage;
    }

    public void setShopEvaluateImage(byte shopEvaluateImage) {
        this.shopEvaluateImage = shopEvaluateImage;
    }
}
