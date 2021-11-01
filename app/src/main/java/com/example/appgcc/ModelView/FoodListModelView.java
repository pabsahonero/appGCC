package com.example.appgcc.ModelView;

import android.app.Application;
import android.content.ClipData;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.appgcc.DB.AppDatabase;
import com.example.appgcc.Entities.Category;
import com.example.appgcc.Entities.Food;

import java.util.List;

public class FoodListModelView extends AndroidViewModel {

    private MutableLiveData<List<Food>> listOfFoods;
    private AppDatabase appDatabase;

    public FoodListModelView(Application application) {
        super(application);
        listOfFoods = new MutableLiveData<>();

        appDatabase = AppDatabase.getDatabase(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Food>>  getItemsListObserver() {
        return listOfFoods;
    }

    public void getAllItemsList(int categoryID) {
        List<Food> itemsList=  appDatabase.foodDao().getAllItemsList(categoryID);
        if(itemsList.size() > 0)
        {
            listOfFoods.postValue(itemsList);
        }else {
            listOfFoods.postValue(null);
        }
    }

    public void insertFoods(Food food) {

        appDatabase.shoppingListDao().insertItems(food);
        getAllItemsList(food.categoryId);
    }

    public void updateItems(Items item) {
        appDatabase.shoppingListDao().updateItems(item);
        getAllItemsList(item.categoryId);
    }

    public void deleteItems(Items item) {
        appDatabase.shoppingListDao().deleteItem(item);
        getAllItemsList(item.categoryId);
    }

}