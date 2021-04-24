package com.example.locationapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.essam.simpleplacepicker.MapActivity;
import com.essam.simpleplacepicker.utils.SimplePlacePicker;
import com.google.android.gms.maps.model.LatLng;

// referenced this website for saving state
// https://sites.google.com/site/jalcomputing/home/mac-osx-android-programming-tutorial/saving-instance-state

public class createNewReminderActivity extends AppCompatActivity {

    private LatLng latLng = null;
    private EditText title;
    private EditText message;
    private TextView theLocation;
    private boolean isSavedInstanceState = false;

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
        else{
            SharedPreferences prefs = getPreferences(MODE_PRIVATE); // singleton
            if (prefs != null){
                title.setText(prefs.getString("title",""));
                message.setText(prefs.getString("message",""));
                theLocation.setText(prefs.getString("latlng",""));
            }
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
        if(title.getText().toString().equals("") || message.getText().toString().equals("") || latLng == null)
            Toast.makeText(this,"Fill in all the needed fields", Toast.LENGTH_LONG).show();
        else{
            NotifDatabase db = NotifDatabase.getDatabase(this);
            db.insert(new Notif(0, title.getText().toString(), message.getText().toString(), latLng.latitude, latLng.longitude));
            //Toast.makeText(this, "Reminder Created", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void toMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("onSave", "onSave");
        isSavedInstanceState = true;
        outState.putString("title", title.getText().toString());
        outState.putString("message", message.getText().toString());
        outState.putString("latlng", theLocation.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (!isSavedInstanceState){ // time to write to prefs
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("title", title.getText().toString());
            editor.putString("message", message.getText().toString());
            editor.putString("latlng", theLocation.getText().toString());
            editor.commit();
            Log.d("tag","savedPrefs");
        }
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
        isSavedInstanceState = false;
    }

    public void clickCancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
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