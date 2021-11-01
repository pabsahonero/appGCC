package com.example.appgcc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgcc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            returnLogin();
        }
        ImageButton btnFood = findViewById(R.id.btnFood);
        ImageButton btnDrink = findViewById(R.id.btnDrink);
        ImageButton btnDessert = findViewById(R.id.btnDessert);
        btnFood.setOnClickListener(view -> {
            Intent intent = new Intent (this, FoodListActivity.class);
            intent.putExtra("category", 0);
            startActivity(intent);
        });
        btnDrink.setOnClickListener(view -> {
            Intent intent = new Intent (this, FoodListActivity.class);
            intent.putExtra("category", 1);
            startActivity(intent);
        });
        btnDessert.setOnClickListener(view -> {
            Intent intent = new Intent (this, FoodListActivity.class);
            intent.putExtra("category", 2);
            startActivity(intent);
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profileItem:
                    Intent intent = new Intent (this, ProfileActivity.class);
                    startActivity(intent);
                    break;
                case R.id.contactItem:
                    Intent intent2 = new Intent (this, ContactActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.logoutItem:
                    mAuth.signOut();
                    returnLogin();
                    break;
                default:
                    return false;
            }
            return true;
        });
    }

    private void returnLogin () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}