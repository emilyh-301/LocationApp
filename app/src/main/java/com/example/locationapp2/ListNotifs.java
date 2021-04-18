package com.example.locationapp2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListNotifs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        if (savedInstanceState != null) {
            //boolean filtered = savedInstanceState.getBoolean("filtered");
        }

        // Set the action bar
        //setSupportActionBar(findViewById(R.id.toolbar));

        RecyclerView recyclerView = findViewById(R.id.lstNotifs);
        NotifListAdapter adapter;
        adapter = new NotifListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //NotifViewModel notifViewModel = new ViewModelProvider(this).get(NotifViewModel.class);
        NotifViewModel notifViewModel = new NotifViewModel(this.getApplication()); //.get(NotifViewModel.class);
        notifViewModel.getAllNotifs().observe(this, adapter::setNotifs);
    }

    public class NotifListAdapter extends RecyclerView.Adapter<NotifListAdapter.NotifViewHolder> {

        public class NotifViewHolder extends RecyclerView.ViewHolder {

            private TextView tv;
            private Notif notif;

            private NotifViewHolder(View itemView) {
                super(itemView);
                tv = tv.findViewById(R.id.textView);
            }
        }
        private final LayoutInflater layoutInflater;
        private List<Notif> notifs; // Cached copy of notifs

        NotifListAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public NotifListAdapter.NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new NotifViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull NotifListAdapter.NotifViewHolder holder, int position) {

        }

        void setNotifs(List<Notif> notifs){
            this.notifs = notifs;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (notifs != null)
                return notifs.size();
            else return 0;
        }
    }

}
