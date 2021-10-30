package com.example.appgcc.Repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.appgcc.Dao.UserDao;
import com.example.appgcc.Entities.User;
import com.example.appgcc.db.AppDatabase;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        userDao = appDatabase.userDao();
        allUsers = userDao.getAll();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user) {
        AppDatabase.dbExecutor.execute(() ->
                userDao.insert(user));
    }

    public void update(User user) {
        AppDatabase.dbExecutor.execute(() ->
                userDao.update(user));
    }

    public void delete(User user) {
        AppDatabase.dbExecutor.execute(() ->
                userDao.delete(user));
    }
}
