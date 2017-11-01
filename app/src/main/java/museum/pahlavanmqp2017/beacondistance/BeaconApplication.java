package museum.pahlavanmqp2017.beacondistance;


import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;
import java.util.UUID;

// Application class to maintain global context
// BeaconManager will work at all times
public class BeaconApplication extends Application{

    private BeaconManager beaconManager;

    @Override
    public void onCreate(){
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener(){
            @Override
            public void onEnteredRegion(BeaconRegion region, List<Beacon> beacons){
                showNotification(
                        "test message", "Second part blah blah"
                );
            }

            @Override
            public void onExitedRegion(BeaconRegion region){
                // Blank, needs to be here for beacon monitor definition
            }
            
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new BeaconRegion(
                        "monitored region",
                        UUID.fromString("6EE4D6A9-DD8E-550E-FF81-783E445F9C5B"),
                        34226, 26639));

            }
        });
    }

    public void showNotification(String title, String message){
        // Where to switch on calling showNotification
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] {notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setting notification parameters, including message and intent related
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

}
