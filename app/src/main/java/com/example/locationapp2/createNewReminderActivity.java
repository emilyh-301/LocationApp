package com.example.locationapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.essam.simpleplacepicker.MapActivity;
import com.essam.simpleplacepicker.utils.SimplePlacePicker;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.List;
import java.lang.Object;

// referenced this website for saving state
// https://sites.google.com/site/jalcomputing/home/mac-osx-android-programming-tutorial/saving-instance-state

public class createNewReminderActivity extends AppCompatActivity {

    private GeofencingClient geofencingClient;
    List<Geofence> geofenceList = new ArrayList<>();
    private LatLng latLng = null;
    private EditText title;
    private EditText message;
    private TextView theLocation;
    private boolean isSavedInstanceState = false;
    private int GEOFENCE_RADIUS_IN_METERS = 4000;
    //private int GEOFENCE_EXPIRATION_IN_MILLISECONDS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_reminder);
        Bundle bundle = getIntent().getExtras();
        title = findViewById(R.id.theTitle);
        message = findViewById(R.id.theMessage);
        theLocation = findViewById(R.id.theLocation);
        geofencingClient = LocationServices.getGeofencingClient(this);

        // saving state either with onSaveInstanceState or shared preferences
        if (savedInstanceState != null) {
            Log.d("onCreate not null", "onCreate not null");
            title.setText(savedInstanceState.getString("title"));
            message.setText(savedInstanceState.getString("message"));
            theLocation.setText(savedInstanceState.getString("latlng"));
        } else {
            SharedPreferences prefs = getPreferences(MODE_PRIVATE); // singleton
            if (prefs != null) {
                title.setText(prefs.getString("title", ""));
                message.setText(prefs.getString("message", ""));
                theLocation.setText(prefs.getString("latlng", ""));
            }
        }

        // if the intent that brought us this activity has latlng info or not
        try {
            double lat = -1.0, lng = -1.0;
            lat = bundle.getDouble("lat");
            lng = bundle.getDouble("long");

            if (lat != -1.0 && lng != -1.0) {
                latLng = new LatLng(lat, lng);
                theLocation.setText(latLng.latitude + ", " + latLng.longitude);
            }
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
    }

    // invoked when the finished button is clicked
    //@SuppressLint("MissingPermission")
    public void backToHome(View view) {
        // add the stuff to the bd on this click
        // id, title, message, lat, lng
        if (title.getText().toString().equals(""))
            Toast.makeText(this, "Add a title", Toast.LENGTH_LONG).show();
        else if(message.getText().toString().equals(""))
            Toast.makeText(this, "Add a message", Toast.LENGTH_LONG).show();
        else if(latLng == null)
            Toast.makeText(this, "Select a location", Toast.LENGTH_LONG).show();
        else {
            //NotifDatabase db = NotifDatabase.getDatabase(this);
            NotifDatabase.insert(new Notif(0, title.getText().toString(), message.getText().toString(), latLng.latitude, latLng.longitude));
            Log.d("TAG", "GEOFENCE after db");

            String entry = title.getText().toString();
            Geofence geo = new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this geofence.
                    .setRequestId(entry)
                    .setCircularRegion(latLng.latitude, latLng.longitude, GEOFENCE_RADIUS_IN_METERS)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build();
            geofenceList.add(geo);

            Log.d("TAG", "GEOFENCE after builder");

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Log.d("TAG", "GEOFENCE after check permissions");
            geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Geofences added
                            Log.d("TAG", "geofence added");
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to add geofences
                            Log.d("TAG", "geofence not added");
                        }
                    });

            title.setText("");
            message.setText("");
            theLocation.setText("");

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

    // must use shared prefs in onPause because going to the map activity
    // and then coming back to this activity doesn't call onSaveInstanceState
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
        title.setText("");
        message.setText("");
        theLocation.setText("");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        PendingIntent geofencePendingIntent = null;
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

}