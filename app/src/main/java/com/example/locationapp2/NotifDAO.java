package com.example.locationapp2;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NotifDAO {

    @Query("SELECT * FROM NotifTable ORDER BY title COLLATE NOCASE, id")
    LiveData<List<Notif>> getAll();

    @Query("SELECT * FROM NotifTable WHERE id = :notifId")
    Notif getById(int notifId);

    @Query("SELECT * FROM NotifTable WHERE title = :notifTitle")
    Notif getByTitle(String notifTitle);

    @Insert
    void insert(Notif... notifs);

    @Update
    void update(Notif... notif);

    @Delete
    void delete(Notif... user);

    @Query("DELETE FROM NotifTable WHERE id = :notifId")
    void delete(int notifId);
}

