package nl.s5630213023.healthcareproject.HeartRate.HeartRate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nl.s5630213023.healthcareproject.R;

public class HeartRate1 extends Fragment implements View.OnClickListener{

    TextView newHeartRate;
    //Google map
    private GoogleMap gMap;
    // Latitude & Longitude
    protected LocationManager locationManager;


    private double latitudine = 0.00 , longitudine = 0.00;

    public static HeartRate1 newInstance() {
        return new HeartRate1();
    }

    public HeartRate1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.heartrate_fragment1, container, false);


        Button btnMeasure = (Button) v.findViewById(R.id.measure);
        btnMeasure.setOnClickListener(this);

        newHeartRate = (TextView) v.findViewById(R.id.newHeartRate);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        TextView date = (TextView) v.findViewById(R.id.date_heartRate);
        date.setText("Current : " + formattedDate);
        showHeartRate();


        //Google map
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


            gMap.setMyLocationEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }

        return v;
    }


    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub

            if (location == null) { return; }
            //gMap.clear();
            latitudine = location.getLatitude();
            longitudine = location.getLongitude();
            LatLng coordinate = new LatLng(latitudine, longitudine);

           /* gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 16));
            gMap.addMarker(new MarkerOptions().position(coordinate).title("I AM HERE").snippet("Your Position"));*/


            /*gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17));
*/
           // Toast.makeText(getActivity().getApplicationContext(), "Latitude " + latitudine + "  Longtitude " + longitudine, Toast.LENGTH_SHORT).show();

            /*MarkerOptions marker = new MarkerOptions().position(new LatLng(latitudine,longitudine)).title("Your current location");
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            gMap.addMarker(marker);*/

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
    switch (v.getId()){
        case R.id.measure:
            Intent MeasuteAvtivity = new Intent(getActivity().getBaseContext(),MeasureActivity.class);
            startActivity(MeasuteAvtivity);
            break;

    }
    }
    private void showHeartRate(){
        Uri u = Uri.parse("content://HeartRateDB");
        String projs[] = {"heartrate"};
        Cursor c = getActivity().getContentResolver().query(u, projs, null, null,"heartRate_id DESC");
        if(c.getCount() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "not found", Toast.LENGTH_LONG).show();

        }else {
            c.moveToNext();
            newHeartRate.setText(c.getString(0)+" bpm");

        }
    }



}