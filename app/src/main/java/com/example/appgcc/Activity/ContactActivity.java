package com.example.appgcc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.appgcc.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void sendWhatsApp (View view) {
        String url = "https://api.whatsapp.com/send?phone="+getString(R.string.whatsapp)+"&text="+Uri.encode(getString(R.string.whatsapp_msg));
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void openCall (View v) {
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:"+getString(R.string.phone)));
        startActivity(i);
    }

    public void openYoutube (View v) {
        Intent i = new Intent (Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://www.youtube.com/watch?v=fZlHDU2Wtxw"));
        startActivity(i);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng gcc = new LatLng(41.7252322, -87.7508841);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.addMarker(new MarkerOptions()
                .position(gcc)
                .title("Calle False 123 - Gordos con Chorizo"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gcc, 15));
        googleMap.setTrafficEnabled(true);
    }
}