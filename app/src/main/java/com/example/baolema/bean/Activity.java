package com.example.baolema.bean;

public class Activity {
    private int activityId;
    private Shop shop;
    private double fullMoney;
    private double reduceMoney;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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
