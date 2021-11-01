package com.example.appgcc.Repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.appgcc.DB.AppDatabase;
import com.example.appgcc.Dao.FoodDao;
import com.example.appgcc.Entities.CategoryWithFood;
import com.example.appgcc.Entities.Food;

import java.util.List;

public class FoodRepository {

    private FoodDao foodDao;
    private List<Food> allFoods;

    public FoodRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        foodDao = appDatabase.foodDao();
        allFoods = foodDao.getAllFoodList();
    }

    public List<Food> getAllFoods() {
        return allFoods;
    }

    public List<CategoryWithFood> getCaterWithFood(Integer catID) {
        return foodDao.getCategoryWithFoods(catID);
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

