package com.example.appgcc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgcc.Adapater.FoodAdapter;
import com.example.appgcc.Entities.Food;
import com.example.appgcc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<Food> foods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.rv_menu);
        foods = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Food food = new Food("Milanesa " + i, "Comida", "10", "Lechuga, Tomate", "abc123pab@gmail.com");
            foods.add(food);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodAdapter(foods);
        recyclerView.setAdapter(adapter);

        TextView tvHello = findViewById(R.id.tvMainWelcome);
        assert user != null;
        String welcome = getText(R.string.tv_main_welcome).toString() + "\n" + user.getEmail();
        tvHello.setText(welcome);

        Button btnAddFood = findViewById(R.id.btnAddFood);
        btnAddFood.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_title))
                    .setMessage(getString(R.string.dialog_msg))
                    .setView(R.layout.dialog_addfood)
                    .setPositiveButton(getString(R.string.alert_yes), (dialog, which) -> {

                    })
                    .setNegativeButton(getString(R.string.alert_no), (dialog, which) -> dialog.cancel())
                    .show();
        });
        Button btnMyFood = findViewById(R.id.btnMyFood);
        btnMyFood.setOnClickListener(view -> {
            Intent intent = new Intent(this, FoodListActivity.class);
            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profileItem:
                    Intent intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);
                    break;
                case R.id.contactItem:
                    Intent intent2 = new Intent(this, ContactActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.logoutItem:
                    confirmLogOut();
                    break;
                default:
                    return false;
            }
            return true;
        });
    }

    private void confirmLogOut() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert_title))
                .setMessage(getString(R.string.alert_msg))
                .setPositiveButton(getString(R.string.alert_yes), (dialog, which) -> {
                    mAuth.signOut();
                    finish();
                })
                .setNegativeButton(getString(R.string.alert_no), (dialog, which) -> dialog.cancel())
                .show();
    }
}