package nl.s5630213023.healthcareproject.HeartRate.exercise;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.InputType;
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
    static int minute;
    static int count;
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
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        formattedTimeRecord = time.format(c.getTime());
        Timer = (TextView) v.findViewById(R.id.Timer);
        editTimer = (EditText)v.findViewById(R.id.editTimer);
        editTimer.setInputType(InputType.TYPE_CLASS_NUMBER);

        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        formattedDateRecord = date.format(c.getTime());

        //Timer



        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                minute = Integer.parseInt(editTimer.getText().toString());
                count = minute*60000;
                btnSave();
                Timer.setText("00:00:00");
                CounterClass timer = new CounterClass(count,1000);
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
        showReadingCompleteDialog();
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



    private void showReadingCompleteDialog(){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            final Ringtone r = RingtoneManager.getRingtone(getContext().getApplicationContext(), notification);
            r.play();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Exercise Completed");
            builder.setMessage("I just wanted to let you rest")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    r.stop();
                                    dialog.cancel();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}