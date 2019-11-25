package com.example.baolema.bean;

import android.graphics.Bitmap;

import java.sql.Timestamp;
import java.util.List;

public class OrderMain {
    private int orderId;
    private int shopId;
    private String shopName;
    private Bitmap shopTradeMark;
    private int temporaryId;
    private double orderPrice;
    private int userId;
    private String orderStatus;
    private Timestamp orderStartTime;
    private Timestamp orderFinishTime;
    private List<OrderInf> orderInfList;

    public OrderMain(String shopName, double orderPrice, String orderStatus) {
        this.shopName = shopName;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
    }

    public OrderMain(Orders orders) {
        this.orderId=orders.getOrderId();
        this.shopId=orders.getShopId();
        this.userId=orders.getUserId();
        this.temporaryId=orders.getTemporaryId();
        this.orderStatus=orders.getOrderStatus();
        this.orderStartTime=orders.getOrderStartTime();
        this.orderFinishTime=orders.getOrderFinishTime();
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

    public Bitmap getShopTradeMark() {
        return shopTradeMark;
    }

    public void setShopTradeMark(Bitmap shopTradeMark) {
        this.shopTradeMark = shopTradeMark;
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

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public List<OrderInf> getOrderInfList() {
        return orderInfList;
    }

    public void setOrderInfList(List<OrderInf> orderInfList) {
        this.orderInfList = orderInfList;
    }

    public int getTemporaryId() {
        return temporaryId;
    }

    public void setTemporaryId(int temporaryId) {
        this.temporaryId = temporaryId;
    }
}
