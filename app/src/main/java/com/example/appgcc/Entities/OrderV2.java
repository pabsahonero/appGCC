package com.example.appgcc.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class OrderV2 {
    @PrimaryKey(autoGenerate = true)
    private int orderID;
    private String userOrderID;
    private float total;

    public OrderV2(int orderID, String userOrderID, float total) {
        this.orderID = orderID;
        this.userOrderID = userOrderID;
        this.total = total;
    }

    public String getUserOrderID() {
        return userOrderID;
    }

    public void setUserOrderID(String userOrderID) {
        this.userOrderID = userOrderID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}