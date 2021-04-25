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

public class ListNotifs extends AppCompatActivity implements MessageDialog.ClickListener {

    NotifViewModel notifViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notifs);
        if (savedInstanceState != null) {
            //boolean filtered = savedInstanceState.getBoolean("filtered");
        }

        NotifDatabase db = NotifDatabase.getDatabase(this);

        RecyclerView recyclerView = findViewById(R.id.lstNotifs);
        NotifListAdapter adapter = new NotifListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //NotifViewModel notifViewModel = new ViewModelProvider(this).get(NotifViewModel.class);
        notifViewModel = new NotifViewModel(this.getApplication()); //.get(NotifViewModel.class);
        notifViewModel.getAllNotifs().observe(this, adapter::setNotifs);
    }

    @Override
    public void onClick(){
        MessageDialog dialog = new MessageDialog();
        Bundle args = new Bundle();
        TextView tv = findViewById(R.id.txtTitle);
        //args.putString("message", NotifDatabase.getNotifByTitle(tv.getText().toString()), listener);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "messageDialog");
    }

    public class NotifListAdapter extends RecyclerView.Adapter<NotifListAdapter.NotifViewHolder> {

        public class NotifViewHolder extends RecyclerView.ViewHolder {
            //private TextView tv;
            private Notif notif;

            private NotifViewHolder(View itemView) {
                super(itemView);
                //tv = tv.findViewById(R.id.textView);
            }
        }
        private final LayoutInflater layoutInflater;
        // notifs is a cached copy of notifs, but what goes here???
        private List<Notif> notifs; // = (List<Notif>) notifViewModel.getAllNotifs();

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
