package com.example.appgcc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgcc.Activity.MenuActivity;
import com.example.appgcc.Entities.Food;
import com.example.appgcc.Repository.FoodRepository;

public class EditActivity extends AppCompatActivity {
    private EditText editName, editPrice, editDescription;
    private Spinner editSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editName = findViewById(R.id.edit_name);
        editPrice = findViewById(R.id.edit_price);
        editDescription = findViewById(R.id.edit_description);
        editSpinner = findViewById(R.id.spinnerEdit);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_dropdown_item);
        editSpinner.setAdapter(spinnerAdapter);

        Food food = (Food) getIntent().getSerializableExtra("item");
        editName.setText(food.getName());
        editPrice.setText(food.getPrice());
        editDescription.setText(food.getDescription());

        Button btnEditFood = findViewById(R.id.btnEditFood);
        btnEditFood.setOnClickListener(view -> {
            updateFood(food);
            returnMenu();
        });

        Button btnDeleteFood = findViewById(R.id.btnDeleteFood);
        btnDeleteFood.setOnClickListener(view -> {
            deleteFood(food);
            returnMenu();
        });
    }

    private void updateFood(Food food) {
        String name = editName.getText().toString();
        String cat = editSpinner.getSelectedItem().toString();
        String price = editPrice.getText().toString();
        String desc = editDescription.getText().toString();
        food.setName(name);
        food.setCategory(cat);
        food.setPrice(price);
        food.setDescription(desc);
        FoodRepository foodRepository = new FoodRepository(getApplication());
        foodRepository.update(food);
    }

    private void deleteFood(Food food) {
        FoodRepository foodRepository = new FoodRepository(getApplication());
        foodRepository.delete(food);
    }

    private void returnMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}