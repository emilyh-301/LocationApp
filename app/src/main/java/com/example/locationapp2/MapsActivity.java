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
    SearchView searchView;
    LatLng harrisburg = new LatLng(40, -76);
    MarkerOptions myMarker = new MarkerOptions().position(new LatLng(40, -76)).draggable(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        searchView = findViewById(R.id.idSearchView);

        // adding on query listener for our search view.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // on below line we are getting the
                // location name from search view.
                String location = searchView.getQuery().toString();

                // below line is to create a list of address
                // where we will store the list of all address.
                List<Address> addressList = null;

                // checking if the entered location is null or not.
                if (location != null || location.equals("")) {
                    // on below line we are creating and initializing a geo coder.
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        // on below line we are getting location from the
                        // location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // on below line we are getting the location
                    // from our list a first position.
                    Address address = addressList.get(0);

                    // on below line we are creating a variable for our location
                    // where we will add our locations latitude and longitude.
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    // on below line we are adding marker to that position.
                    mMap.addMarker(myMarker.position(latLng).title(location));

                    // below line is to animate camera to that position.
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
 /*       mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }
            @Override
            public void onMarkerDrag(Marker marker) {
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng latLng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                lat[0] = marker.getPosition().latitude;
                lng[0] = marker.getPosition().longitude;
                marker.setPosition(latLng);
            }
        });*/
    }

    public void backToSetReminder(View view){
        Intent intent = new Intent(this, createNewReminderActivity.class);
        intent.putExtra("lat", myMarker.getPosition().latitude);
        intent.putExtra("long", myMarker.getPosition().longitude);
        startActivity(intent);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.d("onMarkerDragStart", "onMarkerDragStart");
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d("onMarkerDrag", "onMarkerDrag");
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //LatLng latLng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
        //LatLng latLng = new LatLng(myMarker.getPosition().latitude, myMarker.getPosition().longitude);
        //lat[0] = marker.getPosition().latitude;
        //lng[0] = marker.getPosition().longitude;
        Log.d("position changed", "position changed");
        myMarker.position(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
        mMap.addMarker(myMarker);
    }
}