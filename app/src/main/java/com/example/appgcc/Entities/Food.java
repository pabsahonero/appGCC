package com.example.appgcc.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food")
public class Food {
    @PrimaryKey(autoGenerate = true)
    public int foodID;
    public String foodName;
    public float foodPrice;

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getName() {
        return foodName;
    }

    public void setName(String name) {
        this.foodName = name;
    }

    public float getPrice() {
        return foodPrice;
    }

    public void setPrice(float price) {
        this.foodPrice = price;
    }
}
