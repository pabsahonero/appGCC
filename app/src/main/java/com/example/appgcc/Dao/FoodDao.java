package com.example.appgcc.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.appgcc.Entities.Category;
import com.example.appgcc.Entities.CategoryWithFood;
import com.example.appgcc.Entities.Food;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("Select * from category")
    List<Category> getAllCategoriesList();

    @Query("SELECT * FROM category WHERE categoryID = :categoryID")
    Category getByID(Integer categoryID);

    @Insert
    void insertCategory(Category...categories);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("Select * from food where categoryId = :catId")
    List<Food> getFoodsByCategory(int catId);

    @Transaction
    @Query("SELECT * FROM category where categoryID = :catID")
    List<CategoryWithFood> getCategoryWithFoods(int catID);

    @Query("Select * from food")
    List<Food> getAllFoodList();

    @Insert
    void insertFood(Food food);

    @Update
    void updateFood(Food food);

    @Delete
    void deleteFood(Food food);

}