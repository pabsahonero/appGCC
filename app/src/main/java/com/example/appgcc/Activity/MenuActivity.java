package com.example.appgcc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
            finish();
        }

        TextView tvHello = findViewById(R.id.tvMainWelcome);
        assert user != null;
        String welcome = getText(R.string.tv_main_welcome).toString() + "\n" + user.getEmail();
        tvHello.setText(welcome);
        /*
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
        */
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