package com.example.appgcc.ModelView;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.appgcc.DB.AppDatabase;
import com.example.appgcc.Entities.Category;

import java.util.List;

public class MenuModelView extends AndroidViewModel{

    private MutableLiveData<List<Category>> listOfCategory;
    private AppDatabase appDatabase;

    public MenuModelView(Application application) {
        super(application);
        listOfCategory = new MutableLiveData<>();

        appDatabase = AppDatabase.getDatabase(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Category>>  getCategoryListObserver() {
        return listOfCategory;
    }

    public void getAllCategoryList() {
        List<Category> categoryList=  appDatabase.foodDao().getAllCategoriesList();
        if(categoryList.size() > 0)
        {
            listOfCategory.postValue(categoryList);
        }else {
            listOfCategory.postValue(null);
        }
    }

    public void insertCategory(String catName) {
        Category category = new Category();
        category.name = catName;
        appDatabase.foodDao().insertCategory(category);
        getAllCategoryList();
    }

    public void updateCategory(Category category) {
        appDatabase.foodDao().updateCategory(category);
        getAllCategoryList();
    }

    public void deleteCategory(Category category) {
        appDatabase.foodDao().deleteCategory(category);
        getAllCategoryList();
    }

}
