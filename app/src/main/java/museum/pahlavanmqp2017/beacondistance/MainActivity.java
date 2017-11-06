package museum.pahlavanmqp2017.beacondistance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private BeaconRegion region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconManager = new BeaconManager(this);

        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> list) {
                if(!list.isEmpty()){
                    Beacon nearestBeacon = list.get(0);
                    TextView beaconRSSI = (TextView)findViewById(R.id.nearestRSSI);
                    beaconRSSI.setText(nearestBeacon.getRssi());
                }
            }
        });

        region = new BeaconRegion("ranged region",
                    UUID.fromString("6EE4D6A9-DD8E-550E-FF81-783E445F9C5B"),
                    34226, 26639);
    }

    @Override
    protected void onResume(){
        super.onResume();
        // Request permissions for needed actions, replaced possibly with own UI
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });

    }

    @Override
    protected void onPause(){
        beaconManager.stopRanging(region);

        super.onPause();
    }

    public void onClick_buttonBeginReadings(View view){

    }
}
