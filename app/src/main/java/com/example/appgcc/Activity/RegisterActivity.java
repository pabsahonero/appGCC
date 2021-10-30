package com.example.appgcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appgcc.Entities.User;
import com.example.appgcc.db.DatabaseHelper;
import com.example.appgcc.Dao.CustomersDAO;
import com.example.appgcc.R;
import com.example.appgcc.Entities.Customer;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collection;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etPhone, etEmail, etPassword;
    private TextInputLayout tilFirstName, tilLastName, tilPhone, tilEmail, tilPassword;
    private CustomersDAO customersDAO;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        customersDAO = new CustomersDAO();
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tilFirstName = (TextInputLayout) findViewById(R.id.tilFirstName);
        tilLastName = (TextInputLayout) findViewById(R.id.tilLastName);
        tilPhone = (TextInputLayout) findViewById(R.id.tilPhone);
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        Button btnCancelSignUp = findViewById(R.id.btnCancelSignUp);

        btnCancelSignUp.setOnTouchListener(new View.OnTouchListener() {
            final GestureDetector gestureDetector = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    return super.onDoubleTap(e);
                }
            });
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                cleanData();
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
//        txt_resultado.setText("Hola " + getIntent().getStringExtra("user"));
    }

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
        DatabaseHelper helper = new DatabaseHelper(this);
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

    private void cleanData() {
        etFirstName.getText().clear();
        etLastName.getText().clear();
        etPhone.getText().clear();
        etEmail.getText().clear();
        etPassword.getText().clear();
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
        /*
        user.setFirstName(etFirstName.getText().toString());
        user.setLastName(etLastName.getText().toString());
        user.setPhone(etPhone.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setPassword(etPassword.getText().toString());
        */
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

    private Boolean validateForm (User user) {
        if (user.getFirstName().isEmpty()) {
            tilFirstName.setError(getString(R.string.error_first_name));
            return false;
        } else tilFirstName.setErrorEnabled(false);
        if (user.getLastName().isEmpty()) {
            tilLastName.setError(getString(R.string.error_last_name));
            return false;
        } else tilLastName.setErrorEnabled(false);
        if (user.getPhone().isEmpty()) {
            tilPhone.setError(getString(R.string.error_phone));
            return false;
        } else tilPhone.setErrorEnabled(false);
        if (user.getEmail().isEmpty()) {
            tilEmail.setError(getString(R.string.error_email));
            return false;
        } else tilEmail.setErrorEnabled(false);
        if (user.getPassword().isEmpty()) {
            tilPassword.setError(getString(R.string.error_password));
            return false;
        } else tilPassword.setErrorEnabled(false);

        return true;
    }

    private void setMissingInputs (){
        if (etEmail.getText().toString().trim().isEmpty()) {
            tilEmail.setError(getString(R.string.error_email));
        } else
        if (etPassword.getText().toString().trim().isEmpty()) {
            tilPassword.setError(getString(R.string.error_password));
        } else tilPassword.setErrorEnabled(false);
        if (etFirstName.getText().toString().trim().isEmpty()) {
            tilFirstName.setError(getString(R.string.error_first_name));
        } else tilFirstName.setErrorEnabled(false);
        if (etLastName.getText().toString().trim().isEmpty()) {
            tilLastName.setError(getString(R.string.error_last_name));
        } else tilLastName.setErrorEnabled(false);
        if (etPhone.getText().toString().trim().isEmpty()) {
            tilPhone.setError(getString(R.string.error_phone));
        } else tilPhone.setErrorEnabled(false);
    }
}