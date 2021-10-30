package com.example.appgcc.Entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithOrders {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "userOrderId"
    )
    public List<OrderV2> orders;
}
