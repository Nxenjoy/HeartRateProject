package nl.s5630213023.healthcareproject.HeartRate.HeartRate;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

import nl.s5630213023.healthcareproject.R;
public class HeartRate2 extends Fragment implements View.OnClickListener {

    ListView listViews;
    static ArrayList<Heart> arrayHeartRate = new ArrayList<Heart>();
    HeartRateAdapter adapterHR;
    private LineChart chart;
    public static HeartRate2 newInstance() {
        return new HeartRate2();
    }

    public HeartRate2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.heartrate_fragment2, container, false);

        listViews = (ListView) v.findViewById(R.id.listViewHeartRate);
        adapterHR = new HeartRateAdapter(this.getContext(),arrayHeartRate);
        listViews.setAdapter(adapterHR);
        registerForContextMenu(listViews);

        btnShow();
        return v;
    }

    @Override
    public void onClick(View v) {

    }

    private void btnShow() {
        Uri u = Uri.parse("content://HeartRateDB");
        Cursor c = getActivity().getContentResolver().query(u, null, null, null, null);
        while (c.moveToNext()) {
           arrayHeartRate.add(new Heart(c.getInt(0), c.getInt(1), c.getString(2).toString(), c.getString(3).toString(), c.getString(4).toString() ,c.getString(5).toString() ,c.getString(6).toString()));

        }
    }
}
