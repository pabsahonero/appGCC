package com.example.appgcc.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appgcc.Entities.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orders")
    LiveData<List<Order>> getAll();

    @Query("SELECT * FROM orders WHERE userOrderID = :email")
    List<Order> getOrdersList(String email);

    @Query("SELECT * FROM orders " +
            "INNER JOIN users ON users.userOrderID = orders.userOrderID " +
            "WHERE users.email LIKE :email")
    List<Order> getOrdersByID(String email);

    @Insert
    void insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);
}

