package com.example.locationapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotifDatabase db = NotifDatabase.getDatabase(this);
        //db.insert(new Notif(0,"Test 2", "This is a test", 55.5, 44.4));
    }

    public void newReminder(View view){
        Intent intent = new Intent(this, createNewReminderActivity.class);
        startActivity(intent);
    }

    public void newText(View view){
        Intent intent = new Intent(this, createNewTextActivity.class);
        startActivity(intent);
    }

    public void goToListView(View view){
        Intent intent = new Intent(this, ListNotifs.class);
        startActivity(intent);
    }

}
