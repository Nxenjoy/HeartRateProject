package nl.s5630213023.healthcareproject.HeartRate;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import nl.s5630213023.healthcareproject.HeartRate.HeartRate.HeartRate_MainActivity;
import nl.s5630213023.healthcareproject.HeartRate.bloodpressure.BloodPressure_MainActivity;
import nl.s5630213023.healthcareproject.HeartRate.exercise.Exercise_MainActivity;
import nl.s5630213023.healthcareproject.R;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    TextView newPressure;
    TextView info;
    TextView newHeartRate;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        newPressure = (TextView) findViewById(R.id.newPressure);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        Button measure = (Button) findViewById(R.id.measure);
        measure.setOnClickListener(this);
        Button record = (Button) findViewById(R.id.record);
        record.setOnClickListener(this);
        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);
        Button setting = (Button) findViewById(R.id.setting);
        setting.setOnClickListener(this);
        info = (TextView) findViewById(R.id.info);
        newHeartRate = (TextView) findViewById(R.id.newheartRate);


        showUser();
        showPressure();
        showHeartRate();
        
        //method

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.measure:
                Intent heartRateActivity = new Intent(this, HeartRate_MainActivity.class);
                startActivity(heartRateActivity);
                break;
            case R.id.record:
                Intent pressureActivity = new Intent(this, BloodPressure_MainActivity.class);
                startActivity(pressureActivity);
                break;
            case R.id.start:
                Intent exerciseActivity = new Intent(this, Exercise_MainActivity.class);
                startActivity(exerciseActivity);
                break;
            case R.id.setting:
                Intent settingActivity = new Intent(this, UserSetting.class);
                startActivity(settingActivity);
                break;
            case R.id.login:
                if(login.getText().equals("Log in"))
                {Intent loginActivity = new Intent(this, Login.class);
                startActivity(loginActivity);

                }else if(login.getText().equals("Log out")){
                    login.setText("Log in");

                }

                break;

        }
    }

    private void showPressure(){
        Uri u = Uri.parse("content://PressureDB");
        String projs[] = {"systolic","diastolic"};

        Cursor c = getContentResolver().query(u, projs, null, null,"pressure_id DESC");
        if(c.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_LONG).show();

        }else {
            c.moveToNext();
            newPressure.setText(c.getString(0)+"/"+c.getString(1)+" mmHg");

        }
    }
    private void showUser() {
        Uri u = Uri.parse("content://UserDBs");
        String projs[] = {"Name", "Lastname"};
        String selection = "user_id = ?";
        String selectArg[] = {"1"};
        Cursor c = getContentResolver().query(u, projs, selection, selectArg, null);
        if (c.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_LONG).show();
            login.setText("Log in");
        } else {
            c.moveToNext();
            info.setText(c.getString(0) + " " + c.getString(1));
            login.setText("Log out");

        }
    }

    private void showHeartRate(){
        Uri u = Uri.parse("content://HeartRateDB");
        String projs[] = {"heartrate"};
        Cursor c = getContentResolver().query(u, projs, null, null,"heartRate_id DESC");
        if(c.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_LONG).show();

        }else {
            c.moveToNext();
            newHeartRate.setText(c.getString(0)+" bpm");

        }
    }
}