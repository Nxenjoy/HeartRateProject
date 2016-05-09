package nl.s5630213023.healthcareproject.HeartRate.HeartRate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nl.s5630213023.healthcareproject.R;

public class HeartRate1 extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    TextView newHeartRate;
    TextView status;
    Location location;
    //Google map
    private GoogleMap googleMap;
    // Latitude & Longitude


    private double latitudine = 0.00, longitudine = 0.00;

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
        status = (TextView) v.findViewById(R.id.status);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        TextView date = (TextView) v.findViewById(R.id.date_heartRate);
        date.setText("Current : " + formattedDate);
        showHeartRate();
        showStatus();

        //Google map
        SupportMapFragment m = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.Map));

        m.getMapAsync(this);

        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.measure:
                Intent MeasuteAvtivity = new Intent(getActivity().getBaseContext(), HeartRateMonitor.class);
                startActivity(MeasuteAvtivity);
                getActivity().finish();
                break;

        }
    }

    private void showStatus() {
        Uri u = Uri.parse("content://HeartRateDB");
        String projs[] = {"Status"};
        Cursor c = getActivity().getContentResolver().query(u, projs, null, null, "heartRate_id DESC");
        if (c.getCount() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "not found", Toast.LENGTH_SHORT).show();
        } else {
            c.moveToNext();
            status.setText(c.getString(0));

        }
    }

    private void showHeartRate() {
        Uri u = Uri.parse("content://HeartRateDB");
        String projs[] = {"heartrate"};
        Cursor c = getActivity().getContentResolver().query(u, projs, null, null, "heartRate_id DESC");
        if (c.getCount() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "not found", Toast.LENGTH_SHORT).show();
        } else {
            c.moveToNext();
            newHeartRate.setText(c.getString(0) + " bpm");

        }
    }


    @Override
    public void onMapReady(GoogleMap Map) {
        googleMap = Map;
        setUpMap();
    }

    public void setUpMap() {

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        LocationManager service = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);

        latitudine = location.getLatitude();
        longitudine = location.getLongitude();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitudine, longitudine), 16));

        googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }
}