package nl.s5630213023.healthcareproject.HeartRate.bloodpressure;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nl.s5630213023.healthcareproject.R;


public class BloodPressure1 extends Fragment implements View.OnClickListener {

    TextView newPressure;
    public static BloodPressure1 newInstance() {
        return new BloodPressure1();
    }

    public BloodPressure1() {
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
        View v = inflater.inflate(R.layout.bloodpressure_fragment1,container,false);
        Button btnRecord = (Button)v.findViewById(R.id.recordBP);
        btnRecord.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        TextView date = (TextView)v.findViewById(R.id.date_bloodpressure1);
        date.setText("Current : " + formattedDate);

        TextView detail = (TextView)v.findViewById(R.id.detail_bloodpressure1);
        detail.setText("Measurements less than 120/80 mmHg are generally considered to be within the normal range for adults aged 20 or over.");

        newPressure = (TextView)v.findViewById(R.id.newPressure);
       showPressure();

        return v;
    }

    private void showPressure(){
    Uri u = Uri.parse("content://PressureDB");
    String projs[] = {"systolic","diastolic"};
    Cursor c = getActivity().getContentResolver().query(u, projs, null, null,"pressure_id DESC");
        if(c.getCount() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "not found", Toast.LENGTH_LONG).show();

        }else {
            c.moveToNext();
            newPressure.setText(c.getString(0)+"/"+c.getString(1)+" mmHg");

        }
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.recordBP:
            Intent RecordAvtivity = new Intent(getActivity().getBaseContext(),Record_BloodPressure.class);
            startActivity(RecordAvtivity);
            getActivity().finish();
            break;

    }
    }
}