package nl.s5630213023.healthcareproject.HeartRate.exercise;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nl.s5630213023.healthcareproject.R;


public class Exercise1 extends Fragment implements View.OnClickListener {

    public static Exercise1 newInstance() {
        return new Exercise1();
    }
    EditText editTimer;
    String formattedDateRecord;
    String formattedTimeRecord;

    public Exercise1() {
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
        View v = inflater.inflate(R.layout.exercise_fragment1,container,false);
        Button btnStart = (Button)v.findViewById(R.id.startEX);
        btnStart.setOnClickListener(this);
        String[] type = {"Type","Walking","Running","Fitness","Other",};
        Spinner typeExercise = (Spinner)v.findViewById(R.id.typeExercise);
        ArrayAdapter<String> strType = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,type);
        strType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        typeExercise.setAdapter(strType);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        TextView dateex = (TextView)v.findViewById(R.id.date_exercise);
        dateex.setText("Current : " + formattedDate);

        editTimer = (EditText)v.findViewById(R.id.editTimer);

        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        formattedDateRecord = date.format(c.getTime());

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        formattedTimeRecord = time.format(c.getTime());


        return v;


    }



    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.startEX:
            btnSave();

            break;
    }
    }

    private void btnSave() {
        Uri u = Uri.parse("content://ExerCiseDB");
        ContentValues cv = new ContentValues();
        cv.put("Date",formattedDateRecord.toString());
        cv.put("Time",formattedTimeRecord.toString());
        cv.put("Timer",Integer.parseInt(editTimer.getText().toString()));
        cv.put("Type","Running".toString());
        Uri uri = getActivity().getContentResolver().insert(u,cv);
        Toast.makeText(getActivity().getApplicationContext(), "Start complete", Toast.LENGTH_SHORT).show();
    }
}