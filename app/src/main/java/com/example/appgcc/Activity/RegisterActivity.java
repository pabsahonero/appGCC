package com.example.appgcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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

import com.example.appgcc.db.DatabaseHelper;
import com.example.appgcc.DAO.CustomersDAO;
import com.example.appgcc.R;
import com.example.appgcc.entities.Customer;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collection;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etPhone, etEmail, etPassword;
    private TextInputLayout tilFirstName, tilLastName, tilPhone, tilEmail, tilPassword;
    private CustomersDAO customersDAO;
    private Button btnCancelSignUp;

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
        btnCancelSignUp = findViewById(R.id.btnCancelSignUp);

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
        Customer customer = getCustomer();
        //customer.isComplete()
        if (validateForm()) {
            customersDAO.saveCustomer(customer, this);
            cleanData();
            Toast.makeText(this, getString(R.string.toast_signup_ok), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.toast_signup_failed), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }

    public void volverInicio (View view) {

        Intent inicio = new Intent(this, MainActivity.class);
        startActivity(inicio);
        //Toast.makeText(this, "Vuelve a presionar para volver a la pantalla de inicio", Toast.LENGTH_LONG).show();
    }

    private boolean validateForm() {
        if (!validateEditText(etFirstName, tilFirstName, R.string.error_first_name)) {
            return false;
        }
        if (!validateEditText(etLastName, tilLastName, R.string.error_last_name)) {
            return false;
        }
        if (!validateEditText(etPhone, tilPhone, R.string.error_phone)) {
            return false;
        }
        if (!validateEditText(etEmail, tilEmail, R.string.error_email)) {
            return false;
        }
        if (!validateEditText(etPassword, tilPassword, R.string.error_password)) {
            return false;
        }
        return true;
    }

    private Boolean validateEditText(EditText editText, TextInputLayout textInputLayout, int errorString) {
        if (editText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError(getString(errorString));
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    public void Buscar (View v) {
        DatabaseHelper admin = new DatabaseHelper(this);
        SQLiteDatabase database = admin.getWritableDatabase();

        String email = etEmail.getText().toString();

        if (validateEditText(etEmail, tilEmail, R.string.error_email)) {
            Cursor fila = database.rawQuery
                    ("select firstName, lastName, phone from users where email =" + email, null);

            if (fila.moveToFirst()) {
                etFirstName.setText(fila.getString(0));
                etLastName.setText(fila.getString(1));
                etPhone.setText(fila.getString(2));

                database.close();
            } else {
                Toast.makeText(this, "No existe registro", Toast.LENGTH_LONG).show();
                database.close();
            }

        } else {
            Toast.makeText(this, "Debes ingresar email", Toast.LENGTH_LONG).show();
        }
    }

    public void Eliminar (View v) {
        DatabaseHelper admin = new DatabaseHelper(this);
        SQLiteDatabase database = admin.getWritableDatabase();

        String email = etEmail.getText().toString();

        if (!email.isEmpty()) {
            int cantidad = database.delete("users", "email=" + email, null);
            database.close();

            cleanData();

            if (cantidad==1) {
                Toast.makeText(this, "Email eliminado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El email no existe", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Debes ingresar email", Toast.LENGTH_LONG).show();
            database.close();
        }
    }
    public void Modificar (View v) {
        DatabaseHelper admin = new DatabaseHelper(this);
        SQLiteDatabase database = admin.getWritableDatabase();

        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (validateForm()){
            ContentValues registro = new ContentValues();
            registro.put("email", email);
            registro.put("firstName", firstName);
            registro.put("lastName", lastName);
            registro.put("phone", phone);
            registro.put("password", password);

            int cantidad = database.update("users", registro, "email=" + email, null);

            if (cantidad==1) {
                Toast.makeText(this, "modificado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El email no existe", Toast.LENGTH_LONG).show();
            }

            cleanData();

            database.close();
        } else {
            Toast.makeText(this, "debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}