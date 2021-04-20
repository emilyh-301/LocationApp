package com.example.locationapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.essam.simpleplacepicker.MapActivity;
import com.essam.simpleplacepicker.utils.SimplePlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

public class createNewReminderActivity extends AppCompatActivity {

    LatLng latLng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_reminder);
        Bundle bundle = getIntent().getExtras();
        TextView theLocation;

        double lat = -1.0, lng = -1.0;
        if(bundle.getString("lat") != null){
            lat = Double.parseDouble(bundle.getString("lat"));
        }
        if(bundle.getString("long") != null){
            lng = Double.parseDouble(bundle.getString("long"));
        }
        if(lat != -1.0 && lng != -1.0) {
            latLng = new LatLng(lat, lng);
            theLocation = findViewById(R.id.theLocation);
            theLocation.setText(latLng.latitude + ", " + latLng.longitude);
        }
    }

    public void backToHome(View view){
        // add the stuff to the bd on this click

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    public void placePicker(View view){
        Intent intent = new Intent(this, MapActivity.class);
        Bundle bundle = new Bundle();

        //bundle.putString(SimplePlacePicker.API_KEY, apiKey);
        //bundle.putString(SimplePlacePicker.COUNTRY, country);
        //bundle.putStringArray(SimplePlacePicker.SUPPORTED_AREAS, supportedAreas);
        //intent.putExtras(bundle);
        startActivityForResult(intent, SimplePlacePicker.SELECT_LOCATION_REQUEST_CODE);
    }

}