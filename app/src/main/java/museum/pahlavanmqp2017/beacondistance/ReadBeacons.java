package museum.pahlavanmqp2017.beacondistance;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ReadBeacons extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_beacons);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothLeScanner mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

        ScanSettings mScanSettings = setScanSettings();
        ScanFilter mScanFilter = setScanFilter();
        //setScanSettings();
        //setScanFilter();
        mBluetoothLeScanner.startScan(Arrays.asList(mScanFilter), mScanSettings, mScanCallback);
    }

    protected ScanCallback mScanCallback = new ScanCallback(){
        @Override
        public void onScanResult(int callbackType, ScanResult result){
            ScanRecord mScanRecord = result.getScanRecord();
            byte[] manufacturerData = mScanRecord.getManufacturerSpecificData(224);
            int mRssi = result.getRssi();
        }
    };

    private ScanFilter setScanFilter(){
        ScanFilter.Builder mBuilder = new ScanFilter.Builder();
        ByteBuffer mManufacturerData = ByteBuffer.allocate(23);
        ByteBuffer mManufacturerDataMask = ByteBuffer.allocate(24);
        byte[] uuid = getIdAsByte(UUID.fromString("6EE4D6A9DD8E550EFF81783E445F9C5B"));
        mManufacturerData.put(0, (byte)0xBE);
        mManufacturerData.put(1, (byte)0xAC);
        for(int i=2; i<=17; i++){
            mManufacturerData.put(i, uuid[i-2]);

        }
        for(int i=0; i<=17; i++){
            mManufacturerDataMask.put((byte)0x01);
        }
        mBuilder.setManufacturerData(224, mManufacturerData.array(), mManufacturerDataMask.array());
        ScanFilter mScanFilter = mBuilder.build();
        return mScanFilter;
    }

    private ScanSettings setScanSettings(){
        ScanSettings.Builder mBuilder = new ScanSettings.Builder();
        mBuilder.setReportDelay(0);
        mBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);
        ScanSettings mScanSettings = mBuilder.build();
        return mScanSettings;
    }

    public static byte[] getIdAsByte(java.util.UUID uuid){
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
