package nl.s5630213023.healthcareproject.HeartRate.exercise;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.concurrent.TimeUnit;

import nl.s5630213023.healthcareproject.R;



public class Exercise1 extends Fragment{

    public static Exercise1 newInstance() {
        return new Exercise1();
    }
    EditText editTimer;
    String formattedDateRecord;
    String formattedTimeRecord;

    TextView Timer;
    //Spinner
    static String typeSelect;
    Spinner typeExercise;


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

        //Spinner
        String[] type = {"Type","Walking","Running","Fitness","Other",};
        typeExercise = (Spinner)v.findViewById(R.id.typeExercise);

        ArrayAdapter<String> strType = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,type);
        strType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        typeExercise.setAdapter(strType);

        //Calendar
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

        Timer = (TextView) v.findViewById(R.id.Timer);


        final CounterClass timer = new CounterClass(100000,1000);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave();
                Timer.setText("00:"+editTimer.getText().toString()+":00");
                timer.start();
                            }
        });
        return v;


    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
public class CounterClass extends CountDownTimer{

    public CounterClass(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        long millis = millisUntilFinished;
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        System.out.println(hms);
        Timer.setText(hms);
    }

    @Override
    public void onFinish() {

    }
}


    private void btnSave() {
        Uri u = Uri.parse("content://ExerCiseDB");
        ContentValues cv = new ContentValues();
        cv.put("Date",formattedDateRecord.toString());
        cv.put("Time",formattedTimeRecord.toString());
        cv.put("Timer",Integer.parseInt(editTimer.getText().toString()));
        typeSelect = typeExercise.getSelectedItem().toString();
        if(typeSelect.equals("Type")){
            typeSelect="Unknown";
        }
        cv.put("Type",typeSelect);
        Uri uri = getActivity().getContentResolver().insert(u,cv);
        Toast.makeText(getActivity().getApplicationContext(), "Start complete", Toast.LENGTH_SHORT).show();
    }
}