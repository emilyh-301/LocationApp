package com.example.locationapp2;

import android.app.Application;
import java.util.List;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class NotifViewModel extends AndroidViewModel {
    private LiveData<List<Notif>> notifs;

    public NotifViewModel (Application application) {
        super(application);
        notifs = NotifDatabase.getDatabase(getApplication()).notifDAO().getAll();
    }

    public LiveData<List<Notif>> getAllNotifs() {
        return notifs;
    }
}
