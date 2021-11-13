package com.example.appgcc.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Patterns;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.appgcc.Entities.Customer;
import com.example.appgcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText etFirstName, etLastName, etPhone, etEmail, etPassword;
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersRef = db.collection("users");
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.etFirstName, "[a-zA-Z\\s]+", R.string.error_first_name);
        awesomeValidation.addValidation(this, R.id.etLastName, "[a-zA-Z\\s]+", R.string.error_last_name);
        awesomeValidation.addValidation(this, R.id.etPhone, RegexTemplate.TELEPHONE, R.string.error_phone);
        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS,R.string.error_email);
        awesomeValidation.addValidation(this, R.id.etPassword, ".{6,}",R.string.error_password);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnCancelSignUp = findViewById(R.id.btnCancelSignUp);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(view -> {
            if (awesomeValidation.validate()) {
                registrar();
            } else {
                Toast.makeText(RegisterActivity.this, getString(R.string.toast_signup_failed), Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelSignUp.setOnTouchListener(new View.OnTouchListener() {
            final GestureDetector gestureDetector = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    finish();
                    return super.onDoubleTap(e);
                }
            });
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                cleanData();
                Toast.makeText(RegisterActivity.this, getString(R.string.double_tap), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void cleanData() {
        etFirstName.getText().clear();
        etLastName.getText().clear();
        etPhone.getText().clear();
        etEmail.getText().clear();
        etPassword.getText().clear();
    }

    private void registrar(){
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Customer customer = new Customer(firstName, lastName, email, phone, pass);
                    String userID = firebaseAuth.getUid();
                    DocumentReference documentReference = usersRef.document(userID);
                    documentReference.set(customer);
                    Toast.makeText(RegisterActivity.this, getString(R.string.toast_signup_ok), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String errorCode = ((FirebaseAuthException) Objects.requireNonNull(task.getException())).getErrorCode();
                    setToastError(errorCode);
                }
            }
        });
    }

    private void setToastError (String error) {
        switch (error) {
            case "ERROR_INVALID_EMAIL":
                Toast.makeText(RegisterActivity.this,getString(R.string.invalid_email), Toast.LENGTH_LONG).show();
                etEmail.setError(getString(R.string.invalid_email));
                etEmail.requestFocus();
                break;
            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(RegisterActivity.this, getString(R.string.wrong_password), Toast.LENGTH_LONG).show();
                etPassword.setError(getString(R.string.wrong_password));
                etPassword.requestFocus();
                etPassword.setText("");
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(RegisterActivity.this, getString(R.string.toast_signup_same_user), Toast.LENGTH_LONG).show();
                etEmail.setError(getString(R.string.toast_signup_same_user));
                etEmail.requestFocus();
                break;
            case "ERROR_USER_DISABLED":
                Toast.makeText(RegisterActivity.this, getString(R.string.user_disabled), Toast.LENGTH_LONG).show();
                break;
            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(RegisterActivity.this, getString(R.string.weak_password), Toast.LENGTH_LONG).show();
                etPassword.setError(getString(R.string.weak_password));
                etPassword.requestFocus();
                break;
        }
    }
}