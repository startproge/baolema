package com.example.baolema.bean;

import java.sql.Timestamp;

public class Orders {
    private int orderId;
    private int shopId;
    private int temporaryId;
    private int userId;
    private String orderStatus;
    private Timestamp orderStartTime;
    private Timestamp orderFinishTime;

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

    public int getTemporaryId() {
        return temporaryId;
    }

    public void setTemporaryId(int temporaryId) {
        this.temporaryId = temporaryId;
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
}
