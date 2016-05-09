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
import android.widget.EditText;
import android.widget.Toast;

import nl.s5630213023.healthcareproject.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmailLogin;
    EditText edtPasswordLogin;
    public String password;
    int user_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(this);

        Button register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Medical opinion : " +"\n"+"\n", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        edtEmailLogin = (EditText) findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = (EditText) findViewById(R.id.edtPasswordLogin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                checkUser();
                Intent MenuActivity = new Intent(this, Menu.class);
                if(edtPasswordLogin.getText().toString().equals(password)){

                    Toast.makeText(getApplicationContext(), "E-mail and password is correct", Toast.LENGTH_LONG).show();
                    startActivity(MenuActivity);
                }else {
                    Toast.makeText(getApplicationContext(),"E-mail and password is not correct", Toast.LENGTH_LONG).show();
                }

                    break;
            case R.id.btnRegister:
                Intent RegisActivity = new Intent(this, Register.class);
                startActivity(RegisActivity);
                break;
                }
        }


    private void checkUser() {
        Uri u = Uri.parse("content://UserDBs");
        String projs[] = {"Password"};
        String selection = "Email = ?";
        String selectArg[] = {edtEmailLogin.getText().toString()};
        Cursor c = getContentResolver().query(u, projs, selection, selectArg, null);


        if (c.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Email is not found", Toast.LENGTH_LONG).show();
        }else{
            c.moveToNext();
            password = c.getString(0);
        }

        }


}
