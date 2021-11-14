package com.example.appgcc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgcc.Entities.Food;
import com.example.appgcc.R;
import com.example.appgcc.Repository.FoodRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddFoodActivity extends AppCompatActivity {
    private EditText name, price, description;
    private Spinner spinner;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        name = findViewById(R.id.dialog_name);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        price = findViewById(R.id.dialog_price);
        description = findViewById(R.id.dialog_description);
        Button saveFood = findViewById(R.id.btnSaveFood);

        saveFood.setOnClickListener(view -> {
            if (isNull()) {
                insertFood();
                Toast.makeText(this, getString(R.string.food_added), Toast.LENGTH_SHORT).show();
                returnMenu();
            } else {
                Toast.makeText(this, getString(R.string.dialog_msg), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNull() {
        return !name.getText().toString().isEmpty() && !price.getText().toString().isEmpty() && !description.getText().toString().isEmpty();
    }

    private void insertFood() {
        Food food = new Food(
                name.getText().toString(),
                spinner.getSelectedItem().toString(),
                price.getText().toString(),
                description.getText().toString(),
                user.getEmail()
        );
        FoodRepository foodRepository = new FoodRepository(getApplication());
        foodRepository.insert(food);
    }

    private void returnMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}