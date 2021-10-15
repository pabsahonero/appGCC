package com.example.appgcc.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgcc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

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
                case R.id.ordersItem:
                    Intent intent3 = new Intent (getBaseContext(), OrderLogActivity.class);
                    startActivity(intent3);
                    break;
                default:
                    return false;
            }
            return true;
        });
    }
}