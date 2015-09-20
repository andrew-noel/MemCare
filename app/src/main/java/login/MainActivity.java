package login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import common.CaregiverDAO;
import database.CaregiverFacade;
import database.LoginService;
import lehigh.cse.memcare.R;

public class MainActivity extends AppCompatActivity {


    LoginService loginService;

    private static Button button_register;
    private static Button button_login;
    private static Button button_cleardb;

    private static EditText username;
    private static EditText password;

    private static String username_text;
    private static String password_text;

    public String first_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginService = new LoginService(this);

        //register init
        Registration_OnClickButtonListener();

        //login init
        username = (EditText)findViewById(R.id.editText_username);
        password = (EditText)findViewById(R.id.editText_password);
        username.setText("");
        password.setText("");
        Login_OnClickButtonListener();
        Cleardb_OnClickButtonListener();

    }

    public void login_to_home(String username){
        CaregiverDAO caregiver = CaregiverFacade.retrieve_Caregiver(username);
        Intent intent = new Intent("lehigh.cse.memcare.Home");
        intent.putExtra("first_name", caregiver.get_firstname());
        startActivity(intent);
    }


    public void Login_OnClickButtonListener(){
        button_login = (Button)findViewById(R.id.button_login);
        button_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        username_text = username.getText().toString();
                        password_text = password.getText().toString();
                        if (LoginService.username_exists(username_text)){
                            //Log.d("PASSWORD", "user PASSWORD = " + password_text);
                            if (LoginService.checkPassword(username_text, password_text)) {
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                                login_to_home(username_text);
                            }else{
                                Toast.makeText(MainActivity.this, "Login Failure. Invalid Password", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Username does not exists", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }
    public void Registration_OnClickButtonListener(){
        button_register = (Button)findViewById(R.id.button_register);
        button_register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("lehigh.cse.memcare.registration.Registration");
                        startActivity(intent);
                    }
                }
        );
    }

    public void Cleardb_OnClickButtonListener(){
        button_cleardb = (Button)findViewById(R.id.button_cleardb);
        button_cleardb.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                public void onClick(View v){
                        loginService.clear_database();
                        Toast.makeText(MainActivity.this, "Cleared DataBase", Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
