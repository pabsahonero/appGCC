package com.example.appgcc.ViewModel;
/*
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appgcc.Entities.Food;
import com.example.appgcc.Repository.FoodRepository;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    private final FoodRepository foodRepository;
    private final MutableLiveData<List<Food>> allFoods;

    public FoodViewModel(Application application) {
        super(application);
        foodRepository = new FoodRepository(application);
        allFoods = foodRepository.getAllFoods();
    }

    public void insert(Food food) {
        foodRepository.insert(food);
    }

    public void update(Food food) {
        foodRepository.update(food);
    }

    public void delete(Food food) {
        foodRepository.delete(food);
    }

    public LiveData<List<Food>> getAllFoods() {
        return allFoods;

}
*/