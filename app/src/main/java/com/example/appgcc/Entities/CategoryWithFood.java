package com.example.appgcc.Entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithFood {
    @Embedded public Category category;
    @Relation(
            parentColumn = "categoryID",
            entityColumn = "categoryID"
    )
    public List<Food> foodList;
}
