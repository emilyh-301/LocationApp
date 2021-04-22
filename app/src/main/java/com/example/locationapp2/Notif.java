package com.example.locationapp2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

@Entity(tableName="NotifTable")
public class Notif {

    public Notif(int id, String title, String message, double lat, double lng) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.lat = lat;
        this.lng = lng;
    }

    // Assign 0 to id to have it be auto generated
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "message")
    public String message;

    @ColumnInfo(name = "lat")
    public double lat;

    @ColumnInfo(name = "long")
    public double lng;

}
