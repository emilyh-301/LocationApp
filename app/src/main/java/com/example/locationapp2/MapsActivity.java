package com.example.locationapp2;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

// use the website below for reference
// https://www.geeksforgeeks.org/how-to-add-searchview-in-google-maps-in-android/

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    MarkerOptions myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        myMarker = new MarkerOptions().position(new LatLng(40, -76)).draggable(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double[] lat = new double[1];
        double[] lng = new double[1];

        // Add a marker in Harrisburg and move the camera
        mMap.addMarker(myMarker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40, -76)));
        mMap.setOnMarkerDragListener(this);
    }

    public void backToSetReminder(View view){
        Intent intent = new Intent(this, createNewReminderActivity.class);
        intent.putExtra("lat", myMarker.getPosition().latitude);
        intent.putExtra("long", myMarker.getPosition().longitude);
        startActivity(intent);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        // nothing needs to happen here
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        // nothing needs to happen here
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        myMarker.position(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
    }
}