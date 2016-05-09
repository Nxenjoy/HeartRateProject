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

import nl.s5630213023.healthcareproject.R;

public class UserSetting extends AppCompatActivity implements View.OnClickListener {
    TextView testName;
    TextView testLastName;
    TextView testBirthDay;
    TextView testSex;
    TextView testMedicalCondition;
    TextView testMidicalNote;
    TextView testAllerAndReact;
    TextView testMedications;
    TextView testLowHeart;
    TextView testHightHeart;
    TextView testHowOften;
    TextView testEmContect;
    TextView testBloodType;
    TextView testWeight;
    TextView testHight;
    TextView testContactID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Medical opinion : " +"\n"+"\n", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button Edit = (Button) findViewById(R.id.edit);
        Edit.setOnClickListener(this);
        testName = (TextView) findViewById(R.id.testName);
        testLastName = (TextView) findViewById(R.id.testLastName);
        testBirthDay = (TextView) findViewById(R.id.testBirthDay);
        testSex = (TextView) findViewById(R.id.testSex);
        testMedicalCondition = (TextView) findViewById(R.id.testMedicalCondition);
        testMidicalNote = (TextView) findViewById(R.id.testMidicalNote);
        testAllerAndReact = (TextView) findViewById(R.id.testAllerAndReact);
        testMedications = (TextView) findViewById(R.id.testMedications);
        testLowHeart = (TextView) findViewById(R.id.testLowHeart);
        testHightHeart = (TextView) findViewById(R.id.testHightHeart);
        testHowOften = (TextView) findViewById(R.id.testHowOften);
        testEmContect = (TextView) findViewById(R.id.testEmContect);
        testBloodType = (TextView) findViewById(R.id.testBloodType);
        testWeight = (TextView) findViewById(R.id.testWeight);
        testHight = (TextView) findViewById(R.id.testHight);
        testContactID = (TextView) findViewById(R.id.testContactID);

        showUser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit:
                Intent EditActivity = new Intent(this, EditSetting.class);
                startActivity(EditActivity);
                finish();
                break;
        }
    }

    private void showUser() {
        Uri u = Uri.parse("content://UserDBs");
        String projs[] = {"user_id", "Name", "Lastname", "Birthday", "Sex", "medicalCondition", "medicalNote", "Allergies_reaction", "medication", "lowHeartRate", "highHeartRate", "how_often", "emergencyContract", "emergencyTelephone", "bloodType", "weight", "hight"};
        String selection = "user_id = ?";
        String selectArg[] = {"1"};
        Cursor c = getContentResolver().query(u, projs, selection, selectArg, null);
        if (c.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_SHORT).show();
        } else {
            c.moveToNext();
            testContactID.setText(c.getString(0));
            testName.setText(c.getString(1));
            testLastName.setText(c.getString(2));
            testBirthDay.setText(c.getString(3));
            testSex.setText(c.getString(4));
            testMedicalCondition.setText(c.getString(5));
            testMidicalNote.setText(c.getString(6));
            testAllerAndReact.setText(c.getString(7));
            testMedications.setText(c.getString(8));
            testLowHeart.setText(c.getString(9) +" bgm");
            testHightHeart.setText(c.getString(10) +" bgm");
            testHowOften.setText(c.getString(11) +" minute");
            testEmContect.setText(c.getString(12)+" : " +c.getString(13));
            testBloodType.setText(c.getString(14));
            testWeight.setText(c.getString(15) +"kg");
            testHight.setText(c.getString(16) +" cm");
        }
    }
}
