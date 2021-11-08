package com.example.appgcc.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.appgcc.DB.AppDatabase;
import com.example.appgcc.Dao.FoodDao;
import com.example.appgcc.Entities.Food;

import java.util.List;

public class FoodRepository {

    private FoodDao foodDao;
    private List<Food> allFoods;
    private List<Food> foodsByCategory;

    public FoodRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        foodDao = appDatabase.foodDao();
        allFoods = foodDao.getAllFoodList();
    }

    public List<Food> getFoodsByCategory() {
        return foodsByCategory;
    }

    public List<Food> getAllFoods() {
        return allFoods;
    }

    public List<Food> getFoodsByCategory(String category) {
        return foodDao.getFoodsByCategory(category);
    }
    public List<Food> getFoodsByCreator(String creatorId) {
        return foodDao.getFoodsByCreator(creatorId);
    }

    public void insert(Food food) {
        AppDatabase.dbExecutor.execute(() ->
                foodDao.insertFood(food));
    }

    public void update(Food food) {
        AppDatabase.dbExecutor.execute(() ->
                foodDao.updateFood(food));
    }

    public void delete(Food food) {
        AppDatabase.dbExecutor.execute(() ->
                foodDao.deleteFood(food));
    }
}

