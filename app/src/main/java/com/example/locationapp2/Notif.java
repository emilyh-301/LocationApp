package com.example.locationapp2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="NotifTable")
public class Notif {

    public Notif(int id, String title, String message, String location, boolean perm) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.location = location;
        this.perm = perm;
    }

    // Assign 0 to id to have it be auto generated
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "message")
    public String message;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "perm")
    public boolean perm;
}
