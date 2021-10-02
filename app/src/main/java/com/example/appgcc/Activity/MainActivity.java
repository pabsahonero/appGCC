package com.example.appgcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.appgcc.R;

public class MainActivity extends AppCompatActivity {

    private EditText txt_user, txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_user = (EditText) findViewById(R.id.txtUser);
        txt_password = (EditText) findViewById(R.id.txtPass);
    }

    public void registrar (View view) {
        Intent registro = new Intent (this, RegisterActivity.class);
        registro.putExtra("user", txt_user.getText().toString());
        registro.putExtra("pass", txt_password.getText().toString());
        startActivity(registro);
    }

    public void navegar (View view) {
        Intent web = new Intent (this, ContactActivity.class);
        web.putExtra("user", txt_user.getText().toString());
        startActivity(web);
    }

    public void ingresar (View view) {
        Intent ingreso = new Intent(this, ProfileActivity.class);
        startActivity(ingreso);
    }
}