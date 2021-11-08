package com.example.appgcc.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food")
public class Food {
    @PrimaryKey(autoGenerate = true)
    public int foodID;
    public String name;
    public String category;
    public String price;
    public String description;
    public String creatorID;

    public Food(String name, String category, String price, String description, String creatorID) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.creatorID = creatorID;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }
}
