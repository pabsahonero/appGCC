package com.example.appgcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appgcc.R;
import com.example.appgcc.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private EditText txt_user, txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_user = (EditText) findViewById(R.id.txtUser);
        txt_password = (EditText) findViewById(R.id.txtPass);
    }

    public void createUser (View view) {
        Intent intent = new Intent (this, RegisterActivity.class);
        startActivity(intent);
    }

    public void loginUser (View v) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String email = txt_user.getText().toString();
        String password = txt_password.getText().toString();
        if ((!email.isEmpty()) && (!password.isEmpty())) {
            Cursor query = db.rawQuery
                    ( "Select email, password from customers where email = " + email + " and password = " + password, null );
            if (query.moveToFirst()) {
                txt_user.setText(query.getString(0));
                txt_password.setText(query.getString(1));
                Toast.makeText(this, getString(R.string.login_welcome), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, getString(R.string.login_invalid_user), Toast.LENGTH_SHORT).show();
            }
            db.close();
        } else{
            Toast.makeText(this, getString(R.string.login_complete), Toast.LENGTH_SHORT).show();
        }
    }
}