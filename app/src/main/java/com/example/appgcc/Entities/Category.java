package com.example.appgcc.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {

    @PrimaryKey(autoGenerate = true)
    public int categoryID;
    public String name;

    public Category() {
    }

    public Category(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }
}