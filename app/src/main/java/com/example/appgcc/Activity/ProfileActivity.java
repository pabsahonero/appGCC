package com.example.appgcc.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.appgcc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    private DocumentReference docRef;
    private TextView pFirstName, pLastName, pPhone, pEmail;
    private EditText etNewPass;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pFirstName = findViewById(R.id.pName);
        pLastName = findViewById(R.id.pLastName);
        pPhone = findViewById(R.id.pPhone);
        pEmail = findViewById(R.id.pEmail);
        etNewPass = findViewById(R.id.etNewPass);
        Button btnChange = findViewById(R.id.btnChangePass);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        CollectionReference usersRef = fStore.collection("users");
        String userID = mAuth.getUid();
        docRef = usersRef.document(userID);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.etNewPass, ".{6,}",R.string.error_password);

        btnChange.setOnClickListener(view -> {
            if (awesomeValidation.validate()) {
            String pass = etNewPass.getText().toString().trim();
            user.updatePassword(pass)
                    .addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), getString(R.string.change_ok), Toast.LENGTH_LONG).show())
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), getString(R.string.change_error) + e.getMessage(), Toast.LENGTH_LONG).show());
            docRef.update("password", pass);
            } else {
                Toast.makeText(ProfileActivity.this, getString(R.string.error_password), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUser();
    }

    public void loadUser() {
        docRef.addSnapshotListener(this, (value, error) -> {
            pFirstName.setText(value.getString("firstName"));
            pLastName.setText(value.getString("lastName"));
            pEmail.setText(value.getString("email"));
            pPhone.setText(value.getString("phone"));
        });
    }
}