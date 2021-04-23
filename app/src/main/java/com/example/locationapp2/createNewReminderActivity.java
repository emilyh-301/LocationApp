package com.example.locationapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.essam.simpleplacepicker.MapActivity;
import com.essam.simpleplacepicker.utils.SimplePlacePicker;
import com.google.android.gms.maps.model.LatLng;

public class createNewReminderActivity extends AppCompatActivity {

    LatLng latLng = null;
    EditText title;
    EditText message;
    TextView theLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_reminder);
        Bundle bundle = getIntent().getExtras();
        title = findViewById(R.id.theTitle);
        message = findViewById(R.id.theMessage);
        theLocation = findViewById(R.id.theLocation);

        if(savedInstanceState != null){
            Log.d("onCreate not null", "onCreate not null");
            title.setText(savedInstanceState.getString("title"));
            message.setText(savedInstanceState.getString("message"));
            theLocation.setText(savedInstanceState.getString("latlng"));
        }

        try {
            double lat = -1.0, lng = -1.0;
            lat = bundle.getDouble("lat");
            lng = bundle.getDouble("long");

            if (lat != -1.0 && lng != -1.0) {
                latLng = new LatLng(lat, lng);
                theLocation.setText(latLng.latitude + ", " + latLng.longitude);
            }
        }
        catch (Exception e){
            //System.out.println(e.getMessage());
        }
    }

    public void backToHome(View view){
        // add the stuff to the bd on this click
        // id, title, message, lat, lng
        NotifDatabase db = NotifDatabase.getDatabase(this);
        db.insert(new Notif(0, title.getText().toString(), message.getText().toString(), latLng.latitude, latLng.longitude));
        //Toast.makeText(this, "Reminder Created", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("onSave", "onSave");
        outState.putString("title", title.getText().toString());
        outState.putString("message", message.getText().toString());
        outState.putString("latlng", theLocation.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        onSaveInstanceState(new Bundle());
        Log.d("onPause", "onPause");
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        Log.d("onRestore", "onRestore");
        title.setText(bundle.getString("title"));
        message.setText(bundle.getString("message"));
        theLocation.setText(bundle.getString("latlng"));
    }

    @Override
    public void onResume(){
        super.onResume();
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