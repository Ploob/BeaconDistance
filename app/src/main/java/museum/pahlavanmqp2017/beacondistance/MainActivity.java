package museum.pahlavanmqp2017.beacondistance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        // Request permissions for needed actions, replaced possibly with own UI
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    public void onClick_buttonBeginReadings(View view){

    }
}
