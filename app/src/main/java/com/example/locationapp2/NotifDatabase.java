package com.example.locationapp2;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// Emily Haigh and Avani Sonawane

@Database(entities = {Notif.class}, version = 1, exportSchema = false)
public abstract class NotifDatabase extends RoomDatabase {
    public interface NotifListener {
        void onNotifReturned(Notif notif);
    }

    public abstract NotifDAO notifDAO();

    private static NotifDatabase INSTANCE;

    public static NotifDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotifDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotifDatabase.class, "notif_database")
                            .addCallback(createNotifDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Note this callback will be run
    private static RoomDatabase.Callback createNotifDatabaseCallback =
            new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            insert(new Notif(0, "default content", "here you go", 1.1, 7.1));
            //createNotifTable();
        }
    };

    private static void createNotifTable() {
    }

    public static void getNotif(int id, NotifListener listener) {
        new AsyncTask<Integer, Void, Notif> () {
            protected Notif doInBackground(Integer... ids) {
                return INSTANCE.notifDAO().getById(ids[0]);
            }

            protected void onPostExecute(Notif notif) {
                super.onPostExecute(notif);
                listener.onNotifReturned(notif);
            }
        }.execute(id);
    }

    public static void getNotifByTitle(String title, NotifListener listener) {
        new AsyncTask<String, Void, Notif> () {
            protected Notif doInBackground(String... titles) {
                return INSTANCE.notifDAO().getByTitle(titles[0]);
            }

            protected void onPostExecute(Notif notif) {
                super.onPostExecute(notif);
                listener.onNotifReturned(notif);
            }
        }.execute(title);
    }

    public static void insert(Notif notif) {
        new AsyncTask<Notif, Void, Void> () {
            protected Void doInBackground(Notif... notifs) {
                INSTANCE.notifDAO().insert(notif);
                return null;
            }
        }.execute(notif);
    }

    public static void delete(int notifId) {
        new AsyncTask<Integer, Void, Void> () {
            protected Void doInBackground(Integer... ids) {
                INSTANCE.notifDAO().delete(ids[0]);
                return null;
            }
        }.execute(notifId);
    }

    public static void update(Notif notif) {
        new AsyncTask<Notif, Void, Void> () {
            protected Void doInBackground(Notif... notifs) {
                INSTANCE.notifDAO().update(notif);
                return null;
            }
        }.execute(notif);
    }
}


