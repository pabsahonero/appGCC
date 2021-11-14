package com.example.appgcc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;
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
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    private FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        RecyclerView recyclerView = findViewById(R.id.rv_menu);
        SearchView searchView = findViewById(R.id.searchV);

        FoodRepository foodRepository = new FoodRepository(getApplication());
        List<Food> foods = foodRepository.getAllFoods();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodAdapter(foods);
        recyclerView.setAdapter(adapter);

        TextView tvHello = findViewById(R.id.tvMainWelcome);
        assert user != null;
        String welcome = getText(R.string.tv_main_welcome).toString() + "\n" + user.getEmail();
        tvHello.setText(welcome);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

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
            int itemId = item.getItemId();
            if (itemId == R.id.profileItem) {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.contactItem) {
                Intent intent2 = new Intent(this, ContactActivity.class);
                startActivity(intent2);
            } else if (itemId == R.id.logoutItem) {
                confirmLogOut();
            } else {
                return false;
            }
            return true;
        });
    }/*
    @Override
    public void onStart() {
        super.onStart();

    }
*/
    private void confirmLogOut() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog
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