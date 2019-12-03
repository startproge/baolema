package com.example.baolema.bean;


import java.sql.Timestamp;

public class OrderSum {
    private int orderId;
    private int shopId;
    private String shopName;
    private byte[] shopTradeMark;
    private int temporaryId;
    private double ordersum;
    private int userId;
    private String orderStatus;
    private Timestamp orderStartTime;
    private Timestamp orderFinishTime;


    public byte[] getShopTradeMark() {
        return shopTradeMark;
    }

    public void setShopTradeMark(byte[] shopTradeMark) {
        this.shopTradeMark = shopTradeMark;
    }

    public double getOrdersum() {
        return ordersum;
    }

    public void setOrdersum(double ordersum) {
        this.ordersum = ordersum;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(Timestamp orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public Timestamp getOrderFinishTime() {
        return orderFinishTime;
    }

    public void setOrderFinishTime(Timestamp orderFinishTime) {
        this.orderFinishTime = orderFinishTime;
    }


    public int getTemporaryId() {
        return temporaryId;
    }

    public void setTemporaryId(int temporaryId) {
        this.temporaryId = temporaryId;
    }
}
