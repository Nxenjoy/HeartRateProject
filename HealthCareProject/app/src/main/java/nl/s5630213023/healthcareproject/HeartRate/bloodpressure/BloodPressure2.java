package nl.s5630213023.healthcareproject.HeartRate.bloodpressure;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import nl.s5630213023.healthcareproject.R;

public class BloodPressure2 extends Fragment implements View.OnClickListener {

    ListView listView;
    static ArrayList<BloodPressure> arrayPressure = new ArrayList<BloodPressure>();
    BloodPressureAdapter adapter;


    public static BloodPressure2 newInstance() {
        return new BloodPressure2();
    }

    public BloodPressure2() {
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
        View v = inflater.inflate(R.layout.bloodpressure_fragment2, container, false);

        listView = (ListView) v.findViewById(R.id.listViewPressure);
        adapter = new BloodPressureAdapter(this.getContext(), arrayPressure);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        btnShow();
        return v;
    }

    @Override
    public void onClick(View v) {

    }

    private void btnShow() {
        Uri u = Uri.parse("content://PressureDB");
        Cursor c = getActivity().getContentResolver().query(u, null, null, null, null);
        while (c.moveToNext()) {
            arrayPressure.add(new BloodPressure(c.getInt(0), c.getInt(1),c.getInt(2), c.getString(3).toString(), c.getString(4).toString()));

        }

    }


}

