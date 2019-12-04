package com.example.baolema.bean;


import java.sql.Timestamp;
import java.util.Objects;

public class Orders {
    private int orderId;
    private Integer shopId;
    private Integer userId;
    private Integer temporaryId;
    private Timestamp orderStartTime;
    private Timestamp orderFinishTime;
    private String orderStatus;
    private String orderRemark;


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


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTemporaryId() {
        return temporaryId;
    }

    public void setTemporaryId(Integer temporaryId) {
        this.temporaryId = temporaryId;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return orderId == orders.orderId &&
                Objects.equals(shopId, orders.shopId) &&
                Objects.equals(userId, orders.userId) &&
                Objects.equals(temporaryId, orders.temporaryId) &&
                Objects.equals(orderStartTime, orders.orderStartTime) &&
                Objects.equals(orderFinishTime, orders.orderFinishTime) &&
                Objects.equals(orderStatus, orders.orderStatus) &&
                Objects.equals(orderRemark, orders.orderRemark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, shopId, userId, temporaryId, orderStartTime, orderFinishTime, orderStatus, orderRemark);
    }
}
