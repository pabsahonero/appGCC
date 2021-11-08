package com.example.appgcc.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.appgcc.Dao.FoodDao;
import com.example.appgcc.Entities.Food;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Food.class}, version = 1, exportSchema = false)
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
                            //.addCallback(roomCallback)
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

                Food food1 = new Food("Pizza", "Comida", "500", "Muzzarella, Salsa de Tomate, Morron", "abc123pab@gmail.com");
                Food food2 = new Food("Hamburguesa", "Comida", "300", "Queso Cheddar, Tomate, Lechuga, Aderezos", "abc123pab@gmail.com");
                Food food3 = new Food("Choripan", "Comida", "300", "Chorizo, Aderezos/Salsas", "abc123pab@gmail.com");
                Food food4 = new Food("Cerveza", "Bebida", "200", "Stella Artois o Quilmes", "abc123pab@gmail.com");
                Food food5 = new Food("Gaseosa", "Bebida", "200", "Linea coca-cola", "abc123pab@gmail.com");
                Food food6 = new Food("Flan casero", "Postre", "200", "Con dulce de leche o crema", "abc123pab@gmail.com");
                Food food7 = new Food("Helado", "Postre", "200", "Sabores de crema o al agua", "abc123pab@gmail.com");

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