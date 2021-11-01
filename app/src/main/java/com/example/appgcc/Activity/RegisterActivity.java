package com.example.appgcc.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText etFirstName, etLastName, etPhone, etEmail, etPassword;
    private Button btnSignUp, btnCancelSignUp;
    //private CustomersDAO customersDAO;
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("users");
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //customersDAO = new CustomersDAO();
        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.etFirstName, "[a-zA-Z\\s]+", R.string.error_first_name);
        awesomeValidation.addValidation(this, R.id.etLastName, "[a-zA-Z\\s]+", R.string.error_last_name);
        awesomeValidation.addValidation(this, R.id.etPhone, RegexTemplate.TELEPHONE, R.string.error_phone);
        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS,R.string.error_email);
        awesomeValidation.addValidation(this, R.id.etPassword, ".{6,}",R.string.error_password);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnCancelSignUp = findViewById(R.id.btnCancelSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();
                */

                if (awesomeValidation.validate()) {
                    registrar();
                    /*firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, getString(R.string.toast_signup_ok), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                setToastError(errorCode);
                            }
                        }
                    });*/
                } else {
                    Toast.makeText(RegisterActivity.this, getString(R.string.toast_signup_failed), Toast.LENGTH_SHORT).show();
                }
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
//        txt_resultado.setText("Hola " + getIntent().getStringExtra("user"));
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

                    usersRef.add(customer);

                    Toast.makeText(RegisterActivity.this, getString(R.string.toast_signup_ok), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                    setToastError(errorCode);
                }
            }
        });
    }
/*
    public void signUp(View view) {
        User user = getUser();

        if (validateForm(user)) {
            cleanData();
            Toast.makeText(this, getString(R.string.toast_signup_ok), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.toast_signup_failed), Toast.LENGTH_SHORT).show();
        }
    }

    public void createUser (View view) {
        Customer customer = getCustomer();
        AppDatabase helper = new AppDatabase(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String email = etEmail.getText().toString();
        setMissingInputs();
        if (!customer.isComplete()) {
            Toast.makeText(this, getString(R.string.toast_signup_failed), Toast.LENGTH_SHORT).show();
        } else {
            Cursor fila = db.rawQuery
                    ("Select email from customers where email = " + email, null );
            if (fila.moveToFirst()) {
                etEmail.setText(fila.getString(0));
                Toast.makeText(this, getString(R.string.toast_signup_same_user), Toast.LENGTH_SHORT).show();
            } else {
            customersDAO.saveCustomer(customer, this);
            cleanData();
            Toast.makeText(this, getString(R.string.toast_signup_ok), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void fillData(Customer customer) {
        etFirstName.setText(customer.getFirstName());
        etLastName.setText(customer.getLastName());
        etPhone.setText(customer.getPhone());
        etEmail.setText(customer.getEmail());
        etPassword.setText(customer.getPassword());
    }

    private User getUser() {
        User user = new User(
                etEmail.getText().toString(),
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etPhone.getText().toString(),
                etPassword.getText().toString()
        );

        user.setFirstName(etFirstName.getText().toString());
        user.setLastName(etLastName.getText().toString());
        user.setPhone(etPhone.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setPassword(etPassword.getText().toString());

        return user;
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setFirstName(etFirstName.getText().toString());
        customer.setLastName(etLastName.getText().toString());
        customer.setPhone(etPhone.getText().toString());
        customer.setEmail(etEmail.getText().toString());
        customer.setPassword(etPassword.getText().toString());
        return customer;
    }

    public void searchCustomer (View view) {
        Customer customerByExample = getCustomer();
        if (customerByExample.isComplete()) {
            Collection<Customer> customers = this.customersDAO.getCustomerByExample(customerByExample, this);
            if (customers.isEmpty()) {
                Toast.makeText(this, "No se encontro el usuario", Toast.LENGTH_SHORT).show();
            }
            else {
                Customer customer = customers.iterator().next();
                fillData(customer);
            }
        } else {
            setMissingInputs();
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
*/
    private void setToastError (String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(RegisterActivity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(RegisterActivity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(RegisterActivity.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

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

            case "ERROR_USER_MISMATCH":
                Toast.makeText(RegisterActivity.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(RegisterActivity.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(RegisterActivity.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(RegisterActivity.this, getString(R.string.toast_signup_same_user), Toast.LENGTH_LONG).show();
                etEmail.setError(getString(R.string.toast_signup_same_user));
                etEmail.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(RegisterActivity.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(RegisterActivity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(RegisterActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(RegisterActivity.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(RegisterActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(RegisterActivity.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(RegisterActivity.this, getString(R.string.weak_password), Toast.LENGTH_LONG).show();
                etPassword.setError(getString(R.string.weak_password));
                etPassword.requestFocus();
                break;

        }

    }

}