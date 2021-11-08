package com.example.appgcc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgcc.Adapater.FoodAdapter;
import com.example.appgcc.Entities.Food;
import com.example.appgcc.R;
import com.example.appgcc.Repository.FoodRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    EditText name, category, price, description;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.rv_menu);
        name = findViewById(R.id.dialog_name);
        category = findViewById(R.id.dialog_category);
        price = findViewById(R.id.dialog_price);
        description = findViewById(R.id.dialog_description);

        FoodRepository foodRepository = new FoodRepository(getApplication());
        List<Food> foods = foodRepository.getAllFoods();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodAdapter(foods);
        recyclerView.setAdapter(adapter);

        TextView tvHello = findViewById(R.id.tvMainWelcome);
        assert user != null;
        String welcome = getText(R.string.tv_main_welcome).toString() + "\n" + user.getEmail();
        tvHello.setText(welcome);

        Button btnAddFood = findViewById(R.id.btnAddFood);
        btnAddFood.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddFoodActivity.class);
            startActivity(intent);
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
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
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