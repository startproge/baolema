package com.example.baolema.bean;

public class Activity {
    private int activityId;
    private int shopId;
    private double fullMoney;
    private double reduceMoney;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public double getFullMoney() {
        return fullMoney;
    }

    public void setFullMoney(double fullMoney) {
        this.fullMoney = fullMoney;
    }

    public double getReduceMoney() {
        return reduceMoney;
    }

    public void setReduceMoney(double reduceMoney) {
        this.reduceMoney = reduceMoney;
    }
}
