package nl.s5630213023.healthcareproject.HeartRate.bloodpressure;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nl.s5630213023.healthcareproject.R;

public class Record_BloodPressure extends AppCompatActivity implements View.OnClickListener{
    EditText Systolic;
    EditText Diastolic;
    String formattedDateRecord;
    String formattedTimeRecord;
    String systolic;
    String diastolic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record__blood_pressure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        TextView date_record = (TextView) findViewById(R.id.date_record);
        date_record.setText("Current : " + formattedDate);

        Button recordBP = (Button) findViewById(R.id.recordbtn);
        recordBP.setOnClickListener(this);
        Button cancelBP = (Button) findViewById(R.id.cancelRecord);
        cancelBP.setOnClickListener(this);

        Systolic = (EditText) findViewById(R.id.systolic);
        Systolic.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        Diastolic = (EditText) findViewById(R.id.diastolic);
        Diastolic.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        systolic = Systolic.getText().toString();
        diastolic = Diastolic.getText().toString();


        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        formattedDateRecord = date.format(c.getTime());

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        formattedTimeRecord = time.format(c.getTime());

    }

    @Override
    public void onClick(View v) {
        Intent MainActivity = new Intent(this,BloodPressure_MainActivity.class);
        switch (v.getId()){

            case R.id.recordbtn:
                startActivity(MainActivity);
                btnSave();
                finish();
                break;
            case R.id.cancelRecord:
                startActivity(MainActivity);
                finish();
                break;

        }
    }

    private  void btnSave(){
        Uri u = Uri.parse("content://PressureDB");
        ContentValues cv = new ContentValues();
        cv.put("systolic", Integer.parseInt(Systolic.getText().toString()));
        cv.put("diastolic",Integer.parseInt(Diastolic.getText().toString()));
        cv.put("Date",formattedDateRecord.toString());
        cv.put("Time",formattedTimeRecord.toString());
        Uri uri = getContentResolver().insert(u,cv);
        Toast.makeText(getApplicationContext(), "Record complete", Toast.LENGTH_SHORT).show();
    }
}
