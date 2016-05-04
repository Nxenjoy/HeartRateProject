package nl.s5630213023.healthcareproject.HeartRate.HeartRate;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nl.s5630213023.healthcareproject.R;
public class MeasureActivity extends AppCompatActivity implements  View.OnClickListener {
    Button btnMeasure;
    EditText edtHeartRate;
    String formattedDateRecord;
    String formattedTimeRecord;
    int heartRate;
    static int lowHeartRate,highHeartRate;
    String status;
    static String Name,Lastname,emergencyContract,emergencyTelephone;

    //Google Map
    private GoogleMap gMap;
    protected LocationManager locationManager;
    static double latitudine = 0.00 , longitudine = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);

        btnMeasure = (Button) findViewById(R.id.btnMeasure);
        btnMeasure.setOnClickListener(this);

        edtHeartRate = (EditText) findViewById(R.id.edtHeartRate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        formattedDateRecord = date.format(c.getTime());

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        formattedTimeRecord = time.format(c.getTime());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        showUser();
        //Google map
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            gMap.setMyLocationEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub

            if (location == null) { return; }
            //gMap.clear();
            latitudine = location.getLatitude();
            longitudine = location.getLongitude();


        }
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            // TODO Auto-generated method stub
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnMeasure:

                btnSave();
                Intent HeartRateActivity = new Intent(this, HeartRate_MainActivity.class);
                startActivity(HeartRateActivity);
                finish();
                break;
        }
    }
    private void btnSave() {
        Uri u = Uri.parse("content://HeartRateDB");
        ContentValues cv = new ContentValues();
        cv.put("heartrate", Integer.parseInt(edtHeartRate.getText().toString()));
        cv.put("Date",formattedDateRecord.toString());
        cv.put("Time",formattedTimeRecord.toString());
        cv.put("Latitude",latitudine);
        cv.put("Longitude",longitudine);
        heartRate = Integer.parseInt(edtHeartRate.getText().toString());
        if(heartRate>highHeartRate || heartRate<lowHeartRate) {
            status = "Abnormal";
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(emergencyTelephone, null,"EMERGENCY TO "+emergencyContract  +"\n "+
                    "From "+Name +" " +Lastname + "\n" +
                    " @ Latitudine :  "+ latitudine +" Longitudine : "+ longitudine , null, null);
        }else { status = "Normal";
        }
        cv.put("Status",status);
        Uri uri = getContentResolver().insert(u,cv);
        Toast.makeText(getApplicationContext(), "Measure complete" + latitudine +" "+ longitudine +" "+status, Toast.LENGTH_SHORT).show();
    }



    private void showUser() {
        Uri u = Uri.parse("content://UserDBs");
        String projs[] = {"Name", "Lastname","lowHeartRate", "highHeartRate","emergencyContract","emergencyTelephone"};
        String selection = "user_id = ?";
        String selectArg[] = {"1"};
        Cursor c = getContentResolver().query(u, projs, selection, selectArg, null);
        if (c.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_SHORT).show();
        } else {
            c.moveToNext();
            Name = c.getString(0);
            Lastname = c.getString(1);
            lowHeartRate = c.getInt(2);
            highHeartRate = c.getInt(3);
            emergencyContract = c.getString(4);
            emergencyTelephone = c.getString(5);
            Toast.makeText(getApplicationContext(),"High heart rate" + highHeartRate + "Low heart Rate" + lowHeartRate , Toast.LENGTH_SHORT).show();
        }
    }
}