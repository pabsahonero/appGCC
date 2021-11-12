package com.example.appgcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.appgcc.R;

public class ContactActivity extends AppCompatActivity {

    ImageButton btnWhatsApp;
    TextView btnPhone, btnMeme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        btnWhatsApp = findViewById(R.id.btnWhatsApp);
        btnWhatsApp.setOnClickListener(view -> {
            String url = "https://api.whatsapp.com/send?phone="+getString(R.string.whatsapp)+"&text="+Uri.encode(getString(R.string.whatsapp_msg));
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        btnPhone = findViewById(R.id.txtContactPhone2);
        btnPhone.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:"+getString(R.string.phone)));
            startActivity(i);
        });

        btnMeme = findViewById(R.id.txtAddress2);
        btnMeme.setOnClickListener(view -> {
            Intent i = new Intent (Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.youtube.com/watch?v=fZlHDU2Wtxw"));
            startActivity(i);
        });
    }
    /*
    public void openCall (View v) {
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:"+getString(R.string.phone)));
        startActivity(i);
    }*/
}