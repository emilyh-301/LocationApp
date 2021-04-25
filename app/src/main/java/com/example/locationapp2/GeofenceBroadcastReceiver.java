package com.example.locationapp2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceStatusCodes
                    .getStatusCodeString(geofencingEvent.getErrorCode());
            Log.e("TAG", errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );

            // Send notification and log the transition details.
            //sendNotification(geofenceTransitionDetails);
            Log.i("TAG", "here " + geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e("TAG", "some error here");
        }
    }

    // Send a notification
//    private void sendNotification( String msg ) {
//        Log.i("TAG", "sendNotification: " + msg );
//
//        // Intent to start the main Activity
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.putExtra("msg", msg);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(notificationIntent);
//        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Creating and sending Notification
//        NotificationManager notificatioMng =
//                (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
//        notificatioMng.notify(
//                GEOFENCE_NOTIFICATION_ID,
//                createNotification(msg, notificationPendingIntent));
//    }
//
//    // Create a notification
//    private Notification createNotification(String msg, PendingIntent notificationPendingIntent) {
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
//        notificationBuilder
//                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
//                .setColor(Color.GREEN)
//                .setContentTitle(msg)
//                .setContentText("Geofence Notification")
//                .setContentIntent(notificationPendingIntent)
//                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
//                .setAutoCancel(true);
//        return notificationBuilder.build();
//    }


    private String getGeofenceTransitionDetails(GeofenceBroadcastReceiver geofenceBroadcastReceiver,
                                                int geofenceTransition, List<Geofence> triggeringGeofences) {
        return "in getGeofenceTransitionDetails method";
    }
}
