package com.example.appgcc.Activity;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appgcc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    private DocumentReference docRef;
    private TextView pFirstName, pLastName, pPhone, pEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pFirstName = findViewById(R.id.pName);
        pLastName = findViewById(R.id.pLastName);
        pPhone = findViewById(R.id.pPhone);
        pEmail = findViewById(R.id.pEmail);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        CollectionReference usersRef = fStore.collection("users");
        String userID = mAuth.getUid();
        docRef = usersRef.document(userID);
        Button btnChange = findViewById(R.id.btnChangePass);
        btnChange.setOnClickListener(view -> {
            EditText newPass = new EditText(view.getContext());
            newPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            AlertDialog.Builder resetDialog = new AlertDialog.Builder(view.getContext());
            resetDialog.setTitle(getString(R.string.change_title))
                    .setMessage(getString(R.string.weak_password))
                    .setView(newPass)
                    .setPositiveButton(getString(R.string.alert_yes), (dialogInterface, i) -> {
                        String pass = newPass.getText().toString().trim();
                        if (pass.length()>=6) {
                            user.updatePassword(pass)
                                    .addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), getString(R.string.change_ok), Toast.LENGTH_LONG).show())
                                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), getString(R.string.change_error) + e.getMessage(), Toast.LENGTH_LONG).show());
                            docRef.update("password", pass);
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.weak_password), Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton(getString(R.string.alert_no), (dialogInterface, i) -> dialogInterface.cancel())
                    .show();
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