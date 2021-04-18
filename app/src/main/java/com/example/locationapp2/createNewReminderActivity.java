package com.example.locationapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.essam.simpleplacepicker.MapActivity;
import com.essam.simpleplacepicker.utils.SimplePlacePicker;

public class createNewReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_reminder);
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