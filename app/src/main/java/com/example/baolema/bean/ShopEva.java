package com.example.baolema.bean;


import java.util.Arrays;
import java.util.Objects;


public class ShopEva {

    private int userId;
    private String userName;
    private byte[] userImage;
    private int shopEvaluateId;
    private int shopId;
    private String shopEvaluateContent;
    private byte[] shopEvaluateImage;
    private Double shopGrade;
    private Integer shopEvaluateOrder;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }


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


    public String getShopEvaluateContent() {
        return shopEvaluateContent;
    }

    public void setShopEvaluateContent(String shopEvaluateContent) {
        this.shopEvaluateContent = shopEvaluateContent;
    }


    public byte[] getShopEvaluateImage() {
        return shopEvaluateImage;
    }

    public void setShopEvaluateImage(byte[] shopEvaluateImage) {
        this.shopEvaluateImage = shopEvaluateImage;
    }


    public Double getShopGrade() {
        return shopGrade;
    }

    public void setShopGrade(Double shopGrade) {
        this.shopGrade = shopGrade;
    }


    public Integer getShopEvaluateOrder() {
        return shopEvaluateOrder;
    }

    public void setShopEvaluateOrder(Integer shopEvaluateOrder) {
        this.shopEvaluateOrder = shopEvaluateOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopEva shopEva = (ShopEva) o;
        return userId == shopEva.userId &&
                shopEvaluateId == shopEva.shopEvaluateId &&
                shopId == shopEva.shopId &&
                Objects.equals(userName, shopEva.userName) &&
                Arrays.equals(userImage, shopEva.userImage) &&
                Objects.equals(shopEvaluateContent, shopEva.shopEvaluateContent) &&
                Arrays.equals(shopEvaluateImage, shopEva.shopEvaluateImage) &&
                Objects.equals(shopGrade, shopEva.shopGrade) &&
                Objects.equals(shopEvaluateOrder, shopEva.shopEvaluateOrder);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(userId, userName, shopEvaluateId, shopId, shopEvaluateContent, shopGrade, shopEvaluateOrder);
        result = 31 * result + Arrays.hashCode(userImage);
        result = 31 * result + Arrays.hashCode(shopEvaluateImage);
        return result;
    }
}
