package com.example.appgcc.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appgcc.R;

public class OrderLogActivity extends AppCompatActivity {
    private RecyclerView orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_log);
        orderList = findViewById(R.id.orderList);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }
}