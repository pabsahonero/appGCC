package com.example.appgcc.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.appgcc.Dao.OrderDao;
import com.example.appgcc.Dao.UserDao;
import com.example.appgcc.Entities.OrderV2;
import com.example.appgcc.Entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, OrderV2.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract OrderDao orderDao();

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
                UserDao userDao = INSTANCE.userDao();

                User user1 = new User("99", "99", "99", "99", "99");
                User user2 = new User("88", "88", "88", "88", "88");

                userDao.insert(user1);
                userDao.insert(user2);
            });
        }
    };
}