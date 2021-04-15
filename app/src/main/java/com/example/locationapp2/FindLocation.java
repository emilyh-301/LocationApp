package com.example.locationapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Use the place picker functionality inside of the Places Plugin, to show UI for
 * choosing a map location. Once selected, return to the previous location with a
 * CarmenFeature to extract information from for whatever use that you want.
 */
public class FindLocation extends AppCompatActivity {

    private TextView selectedLocationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);


    }

}
