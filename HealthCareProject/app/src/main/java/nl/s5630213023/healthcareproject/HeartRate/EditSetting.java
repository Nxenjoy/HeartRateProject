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
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import nl.s5630213023.healthcareproject.R;

public class EditSetting extends AppCompatActivity implements View.OnClickListener {
    EditText editName,editLastName,editBirthDay,editMedicalCondition,editMidicalNote,editAllerAndReact;
    EditText editMedications,editLowHeart,editHightHeart,editHowOften,editEmContect,editWeight,editHight;
    TextView editContactID;
    EditText editEmTelephone;

    //Spinner BloodType
    static String typeBloodSelect;
    Spinner editBloodType;

    //Spinner Sex
    static String typeSexSelect;
    Spinner editSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_setting);
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
        Button Save = (Button) findViewById(R.id.save);
        Save.setOnClickListener(this);

        Button Cancel = (Button) findViewById(R.id.cancel);
        Cancel.setOnClickListener(this);

        editName = (EditText) findViewById(R.id.editName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editBirthDay = (EditText) findViewById(R.id.editBirthDay);
        String[] Sextype = {"Male","Female"};
        editSex = (Spinner) findViewById(R.id.editSex);
        ArrayAdapter<String> strSexType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Sextype);
        strSexType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        editSex.setAdapter(strSexType);

        editMedicalCondition = (EditText) findViewById(R.id.editMedicalCondition);
        editMidicalNote = (EditText) findViewById(R.id.editMidicalNote);
        editAllerAndReact = (EditText) findViewById(R.id.editAllerAndReact);
        editMedications = (EditText) findViewById(R.id.editMedications);
        editLowHeart = (EditText) findViewById(R.id.editLowHeart);
        editLowHeart.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        editHightHeart = (EditText) findViewById(R.id.editHightHeart);
        editHightHeart.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        editHowOften = (EditText) findViewById(R.id.editHowOften);
        editHowOften.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        editEmContect = (EditText) findViewById(R.id.editEmContect);
        editEmTelephone = (EditText) findViewById(R.id.editEmTelephone);
        editEmTelephone.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        String[] type = {"A","B","AB","O"};
        editBloodType = (Spinner) findViewById(R.id.editBloodType);
        ArrayAdapter<String> strType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,type);
        strType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        editBloodType.setAdapter(strType);

        editWeight = (EditText) findViewById(R.id.editWeight);
        editWeight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editHight = (EditText) findViewById(R.id.editHight);
        editHight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editContactID = (TextView) findViewById(R.id.editContactID);
        showUser();
    }

    @Override
    public void onClick(View v) {
        Intent UserSettingActivity = new Intent(this, UserSetting.class);
        switch (v.getId()) {
            case R.id.save:
                startActivity(UserSettingActivity);
                editUser();
                finish();

                break;
            case R.id.cancel:
                startActivity(UserSettingActivity);
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
            Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_LONG).show();
        } else {
            c.moveToNext();
            editContactID.setText(c.getString(0));
            editName.setText(c.getString(1));
            editLastName.setText(c.getString(2));
            editBirthDay.setText(c.getString(3));

            for(int i = 0; i < editSex.getCount(); i++){
                if(editSex.getItemAtPosition(i).toString().equals(c.getString(4))){
                    editSex.setSelection(i);
                    break;
                }}

            editMedicalCondition.setText(c.getString(5));
            editMidicalNote.setText(c.getString(6));
            editAllerAndReact.setText(c.getString(7));
            editMedications.setText(c.getString(8));
            editLowHeart.setText(c.getString(9));
            editHightHeart.setText(c.getString(10));
            editHowOften.setText(c.getString(11));
            editEmContect.setText(c.getString(12));
            editEmTelephone.setText(c.getString(13));

            for(int i = 0; i < editBloodType.getCount(); i++){
                if(editBloodType.getItemAtPosition(i).toString().equals(c.getString(14))){
                    editBloodType.setSelection(i);
                    break;
                }
            }

            editWeight.setText(c.getString(15));
            editHight.setText(c.getString(16));
        }
    }
    private void editUser(){
        Uri u = Uri.parse("content://UserDBs");
        String selection = "user_id = ?";
        String selectArg[] = {"1"};
        //Cursor c = getContentResolver().query(u, projs, selection, selectArg, null);
        ContentValues i = new ContentValues();
        i.put("Name", editName.getText().toString());
        i.put("Lastname", editLastName.getText().toString());
        i.put("Birthday", editBirthDay.getText().toString());
        typeSexSelect = editSex.getSelectedItem().toString();
        i.put("Sex", typeSexSelect);
        i.put("medicalCondition", editMedicalCondition.getText().toString());
        i.put("medicalNote", editMidicalNote.getText().toString());
        i.put("Allergies_reaction", editAllerAndReact.getText().toString());
        i.put("medication", editMedications.getText().toString());
        i.put("lowHeartRate", Integer.parseInt(editLowHeart.getText().toString()));
        i.put("highHeartRate", Integer.parseInt(editHightHeart.getText().toString()));
        i.put("how_often", Integer.parseInt(editHowOften.getText().toString()));
        i.put("emergencyContract", editEmContect.getText().toString());
        i.put("emergencyTelephone", editEmTelephone.getText().toString());
        typeBloodSelect = editBloodType.getSelectedItem().toString();
        i.put("bloodType", typeBloodSelect);
        i.put("weight", Integer.parseInt(editWeight.getText().toString()));
        i.put("hight", Integer.parseInt(editHight.getText().toString()));


        int uri = getContentResolver().update(u,i,selection,selectArg);
    }




}
