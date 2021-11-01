package com.example.appgcc.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.appgcc.Dao.FoodDao;
import com.example.appgcc.Entities.Category;
import com.example.appgcc.Entities.Food;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Food.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FoodDao foodDao();

    private static final String DATABASE_NAME = "app_database";
    private static volatile AppDatabase INSTANCE;
    private static final int THREADS = 4;
    public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            dbExecutor.execute(() -> {
                FoodDao foodDao = INSTANCE.foodDao();

                Category category1 = new Category(0, "Foods");
                Category category2 = new Category(1, "Drinks");
                Category category3 = new Category(2, "Desserts");
                Food food1 = new Food("Pizza", 0, 500, "Muzzarella, Salsa de Tomate, Morron");
                Food food2 = new Food("Hamburguesa", 0, 300, "Queso Cheddar, Tomate, Lechuga, Aderezos");
                Food food3 = new Food("Choripan", 0, 300, "Chorizo, Aderezos/Salsas");
                Food food4 = new Food("Cerveza", 1, 200, "Stella Artois o Quilmes");
                Food food5 = new Food("Gaseosa", 1, 200, "Linea coca-cola");
                Food food6 = new Food("Flan casero", 2, 200, "Con dulce de leche o crema");
                Food food7 = new Food("Helado", 2, 200, "Sabores de crema o al agua");

                foodDao.insertCategory(category1);
                foodDao.insertCategory(category2);
                foodDao.insertCategory(category3);
                foodDao.insertFood(food1);
                foodDao.insertFood(food2);
                foodDao.insertFood(food3);
                foodDao.insertFood(food4);
                foodDao.insertFood(food5);
                foodDao.insertFood(food6);
                foodDao.insertFood(food7);
            });
        }
    };
}