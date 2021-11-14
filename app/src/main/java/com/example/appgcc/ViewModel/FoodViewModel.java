package com.example.appgcc.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.appgcc.Entities.Food;
import com.example.appgcc.Repository.FoodRepository;

import java.util.List;

public class FoodViewModel {

    private FoodRepository foodRepository;
    private LiveData<List<Food>> allFoods;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);
        allFoods = foodRepository.getAllFoods();
    }

    public void insert(Food food) {
        repository.insert(food);
    }

    public void update(Food food) {
        repository.update(food);
    }

    public void delete(Food food) {
        repository.delete(food);
    }

    public void deleteAllFoods() {
        repository.deleteAllFoods();
    }

    public LiveData<List<Food>> getAllFoods() {
        return allFoods;
    }
}
}
