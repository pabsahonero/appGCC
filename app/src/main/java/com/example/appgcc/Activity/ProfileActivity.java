package com.example.appgcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appgcc.Entities.Customer;
import com.example.appgcc.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("users");
    private TextInputEditText etFirstName, etLastName, etPhone, etEmail, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_password);

        loadNotes();
    }
    public void loadNotes() {
        usersRef.whereEqualTo("email", "abc123pab@gmail.com")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Customer customer = documentSnapshot.toObject(Customer.class);
                            //Customer.setDocumentId(documentSnapshot.getId());
                            //String documentId = note.getDocumentId();
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