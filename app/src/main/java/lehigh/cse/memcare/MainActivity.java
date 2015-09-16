package lehigh.cse.memcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper myDb;

    private static Button button_register;
    private static Button button_login;

    private static EditText username;
    private static EditText password;

    private static String username_text;
    private static String password_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDb = new DatabaseHelper(this);

        //register init
        Registration_OnClickButtonListener();

        //login init
        username = (EditText)findViewById(R.id.editText_username);
        password = (EditText)findViewById(R.id.editText_password);
        Login_OnClickButtonListener();
    }

    public void login_to_home(String username, String password){
        //String first_name = myDb.get_first_Name(username, password);

        Intent intent = new Intent("lehigh.cse.memcare.Home");
        startActivity(intent);
        //Intent intentBundle = new Intent(MainActivity.this, Home.class);
        //Bundle bundle = new Bundle();
        //bundle.putString("first_name", first_name);
        //intentBundle.putExtras(bundle);
        //startActivity(intentBundle);

    }
    public void Login_OnClickButtonListener(){
        button_login = (Button)findViewById(R.id.button_login);
        button_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        username_text = username.getText().toString();
                        password_text = password.getText().toString();
                        if (myDb.check_if_user_exists(username_text)){
                            //Log.d("PASSWORD", "user PASSWORD = " + password_text);
                            if (myDb.checkPassword(username_text, password_text)) {
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                                login_to_home(username_text, password_text);
                            }else{
                                Toast.makeText(MainActivity.this, "Login Failure. Invalid Password", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Username does no exists", Toast.LENGTH_LONG).show();
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
                        Intent intent = new Intent("lehigh.cse.memcare.Registration");
                        startActivity(intent);
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
