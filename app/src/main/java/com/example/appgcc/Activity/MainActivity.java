package com.example.appgcc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.appgcc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText txt_user, txt_password;
    private FirebaseAuth mAuth;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user !=null) {
            Intent intent = new Intent (MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }

        txt_user = findViewById(R.id.txtUser);
        txt_password = findViewById(R.id.txtPass);
        Button btnSignUp = findViewById(R.id.btnSignUpHome);
        Button btnLogIn = findViewById(R.id.btnLogIn);
        Button btnForgot = findViewById(R.id.btnForgotPassword);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.txtUser, Patterns.EMAIL_ADDRESS,R.string.hint_email);
        awesomeValidation.addValidation(this, R.id.txtPass, ".{6,}", R.string.hint_password);

        btnLogIn.setOnClickListener(view -> {
            if (awesomeValidation.validate()){
                String email = txt_user.getText().toString();
                String pass = txt_password.getText().toString();

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(intent);
                    } else {
                        String errorCode = ((FirebaseAuthException) Objects.requireNonNull(task.getException())).getErrorCode();
                        setToastError(errorCode);
                    }
                });
            }
        });

        btnSignUp.setOnClickListener(view -> {
            Intent intent = new Intent (MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnForgot.setOnClickListener(view -> sendResetLink());
    }

    private void sendResetLink() {
        EditText resetEmail = new EditText(this);
        resetEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        AlertDialog.Builder resetDialog = new AlertDialog.Builder(this);
        resetDialog.setTitle(getString(R.string.forgot_title))
                .setMessage(getString(R.string.forgot_msg))
                .setView(resetEmail)
                .setPositiveButton(getString(R.string.alert_yes), (dialogInterface, i) -> {
                    String email = resetEmail.getText().toString();
                    mAuth.sendPasswordResetEmail(email)
                            .addOnSuccessListener(unused -> Toast.makeText(MainActivity.this, getString(R.string.forgot_ok), Toast.LENGTH_LONG).show())
                            .addOnFailureListener(e -> Toast.makeText(MainActivity.this, getString(R.string.forgot_error) + e.getMessage(), Toast.LENGTH_LONG).show());
                }).setNegativeButton(getString(R.string.alert_no), (dialogInterface, i) -> dialogInterface.cancel())
                .show();
    }

    private void setToastError (String error) {
        switch (error) {
            case "ERROR_INVALID_EMAIL":
                Toast.makeText(MainActivity.this,getString(R.string.invalid_email), Toast.LENGTH_LONG).show();
                txt_user.setError(getString(R.string.invalid_email));
                txt_user.requestFocus();
                break;
            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(MainActivity.this, getString(R.string.wrong_password), Toast.LENGTH_LONG).show();
                txt_password.setError(getString(R.string.wrong_password));
                txt_password.requestFocus();
                txt_password.getText().clear();
                break;
            case "ERROR_USER_DISABLED":
                Toast.makeText(MainActivity.this, getString(R.string.user_disabled), Toast.LENGTH_LONG).show();
                break;
            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(MainActivity.this, getString(R.string.user_not_found), Toast.LENGTH_LONG).show();
                break;
            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(MainActivity.this, getString(R.string.weak_password), Toast.LENGTH_LONG).show();
                txt_password.setError(getString(R.string.weak_password));
                txt_password.requestFocus();
                break;
        }
    }
}