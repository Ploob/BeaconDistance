package museum.pahlavanmqp2017.beacondistance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick_buttonBeginReadings(View view){
        // BeginReadings button pushed, moved to reading view
        // Move to the ReadBeacons.class
        Intent intent = new Intent(this, ReadBeacons.class);
        startActivity(intent);
    }
}
