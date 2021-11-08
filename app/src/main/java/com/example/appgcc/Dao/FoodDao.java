package com.example.appgcc.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appgcc.Entities.Food;

import java.util.List;

@Dao
public interface FoodDao {

    @Query("Select * from food where categoryId = :catId order by foodID")
    List<Food> getFoodsByCategory(int catId);

    @Query("Select * from food")
    List<Food> getAllFoodList();

    @Query("Select * from food where creatorID = :creatorId")
    List<Food> getFoodsByCreator(String creatorId);

    @Insert
    void insertFood(Food food);

    @Update
    void updateFood(Food food);

    @Delete
    void deleteFood(Food food);

}