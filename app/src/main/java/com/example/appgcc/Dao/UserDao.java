package com.example.appgcc.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appgcc.Entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM users WHERE email = :email")
    User getByID(String email);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
