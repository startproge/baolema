package com.example.baolema.bean;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;


public class OrderSum {


    private int orderId;
    private Integer shopId;
    private String shopName;
    private byte[] shopTrademark;
    private Integer temporaryId;
    private Double ordersum;
    private Integer userId;
    private String orderStatus;
    private Timestamp orderStartTime;
    private Timestamp orderFinishTime;


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    public byte[] getShopTrademark() {
        return shopTrademark;
    }

    public void setShopTrademark(byte[] shopTrademark) {
        this.shopTrademark = shopTrademark;
    }


    public Integer getTemporaryId() {
        return temporaryId;
    }

    public void setTemporaryId(Integer temporaryId) {
        this.temporaryId = temporaryId;
    }


    public Double getOrdersum() {
        return ordersum;
    }

    public void setOrdersum(Double ordersum) {
        this.ordersum = ordersum;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderSum orderSum = (OrderSum) o;
        return orderId == orderSum.orderId &&
                Objects.equals(shopId, orderSum.shopId) &&
                Objects.equals(shopName, orderSum.shopName) &&
                Arrays.equals(shopTrademark, orderSum.shopTrademark) &&
                Objects.equals(temporaryId, orderSum.temporaryId) &&
                Objects.equals(ordersum, orderSum.ordersum) &&
                Objects.equals(userId, orderSum.userId) &&
                Objects.equals(orderStatus, orderSum.orderStatus) &&
                Objects.equals(orderStartTime, orderSum.orderStartTime) &&
                Objects.equals(orderFinishTime, orderSum.orderFinishTime);
    }

    public void setTemporaryId(int temporaryId) {
        this.temporaryId = temporaryId;
    }
}
