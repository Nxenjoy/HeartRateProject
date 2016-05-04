package nl.s5630213023.healthcareproject.HeartRate;

import android.content.ContentValues;
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
import android.widget.EditText;
import android.widget.Toast;

import nl.s5630213023.healthcareproject.R;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText edtNameRegis;
    EditText edtLastNameRegis;
    EditText edtEmailRegis;
    EditText edtPasswordRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(this);
        edtNameRegis = (EditText) findViewById(R.id.edtNameRegis);
        edtLastNameRegis = (EditText) findViewById(R.id.edtLastNameRegis);
        edtEmailRegis = (EditText) findViewById(R.id.edtEmailRegis);
        edtPasswordRegis = (EditText) findViewById(R.id.edtPasswordRegis);
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
            case R.id.btnRegister:
                Intent MenuActivity = new Intent(this, Menu.class);
                startActivity(MenuActivity);
                btnRegister();
                btnShow();
                break;
        }
    }

    private  void btnRegister(){
        Uri u = Uri.parse("content://UserDBs");
        ContentValues cv = new ContentValues();
        cv.put("Name", edtNameRegis.getText().toString());
        cv.put("Lastname",edtLastNameRegis.getText().toString());
        cv.put("Birthday","00/00/00".toString());
        cv.put("Sex","Male".toString());
        cv.put("medicalCondition", "Condition".toString());
        cv.put("medicalNote", "Note".toString());
        cv.put("Allergies_reaction", "reaction".toString());
        cv.put("medication", "Medication".toString());
        cv.put("lowHeartRate",40);
        cv.put("highHeartRate",150);
        cv.put("how_often",30);
        cv.put("emergencyContract", "Contract".toString());
        cv.put("emergencyTelephone","080-000-0000".toString());
        cv.put("bloodType", "Type".toString());
        cv.put("weight", 50);
        cv.put("hight", 170);
        cv.put("Email", edtEmailRegis.getText().toString());
        cv.put("Password", edtPasswordRegis.getText().toString());
        Uri uri = getContentResolver().insert(u,cv);
        Toast.makeText(getApplicationContext(), "Register complete", Toast.LENGTH_SHORT).show();
    }

    private  void btnShow() {
        Uri u = Uri.parse("content://UserDBs");
        String projs[] = {"user_id","Name","Lastname","Email","Password"};
        Cursor c = getContentResolver().query(u,projs, null, null, null);
        while (c.moveToNext()) {
            String line = "UserID : "+c.getString(0).toString() + "  Name : " + c.getString(1).toString() + "  LastName :" + c.getString(2).toString() + "   Email : " +c.getString(3).toString() + " Password : " +c.getString(4).toString();
            showMessage(line);
        }

    }

    private void showMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
