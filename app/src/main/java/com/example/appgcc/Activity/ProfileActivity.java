package com.example.appgcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appgcc.Entities.Customer;
import com.example.appgcc.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("users");
    private EditText etFirstName, etLastName, etPhone, etEmail, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_password);
        loadUser();
    }

    public void loadUser() {
        usersRef.whereEqualTo("email", currentUser.getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Customer customer = documentSnapshot.toObject(Customer.class);
                            String firstName = customer.getFirstName();
                            String lastName = customer.getLastName();
                            String phone = customer.getPhone();
                            String email = customer.getEmail();
                            String pass = customer.getPassword();
                            etFirstName.setText(firstName);
                            etLastName.setText(lastName);
                            etPhone.setText(phone);
                            etEmail.setText(email);
                            etPass.setText(pass);
                        }
                    }
                });
    }
}