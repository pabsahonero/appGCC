package com.example.appgcc.Activity;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgcc.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ContactActivity extends AppCompatActivity {

    private TextView isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        isOpen = findViewById(R.id.tvIsOpen);
        ImageButton btnWhatsApp = findViewById(R.id.btnWhatsApp);
        btnWhatsApp.setOnClickListener(view -> {
            String url = "https://api.whatsapp.com/send?phone="+getString(R.string.whatsapp)+"&text="+Uri.encode(getString(R.string.whatsapp_msg));
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        Button btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.whatsapp));
			sendIntent.setType("text/plain");
			Intent shareIntent = Intent.createChooser(sendIntent, null);
			startActivity(shareIntent);
        });

        TextView btnPhone = findViewById(R.id.txtContactPhone2);
        btnPhone.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:"+getString(R.string.phone)));
            startActivity(i);
        });

        TextView btnMeme = findViewById(R.id.txtAddress2);
        btnMeme.setOnClickListener(view -> {
            Intent i = new Intent (Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.youtube.com/watch?v=fZlHDU2Wtxw"));
            startActivity(i);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOpen();
    }

    private void checkOpen() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH", Locale.US);
        int hour = parseInt(dateFormat.format(calendar.getTime()));
        if (hour >= 10 && hour < 20) isOpen.setText(getString(R.string.is_open));
        else isOpen.setText(getString(R.string.is_closed));
    }
}