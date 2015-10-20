package ui.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import midtier.login.LoginService;
import lehigh.cse.memcare.R;

import java.lang.String;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private static TextView header;
    private static EditText username;
    private static EditText password;

    LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this, new LoginService(this));

        //register buttonn
        Login_OnClickButtonListener();
        ClearDB_OnClickButtonListener();
        RegistrationLink_OnClickListener();

        //text handles
        header =   (TextView) findViewById(R.id.textView_header);
        username = (EditText) findViewById(R.id.editText_username);
        password = (EditText) findViewById(R.id.editText_password);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Sansation-LightItalic.ttf");

        //initialize values
        header.setTypeface(type);
        username.setText("");
        password.setText("");

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
        // automatically handle clicks on the HomeActivity1/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Login_OnClickButtonListener() {
        Button button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onLoginClicked();
                    }
                }
        );
    }

    public void RegistrationLink_OnClickListener(){
        TextView link = (TextView) findViewById(R.id.textView_registerLink);
        link.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        presenter.onRegistrationClick();
                    }
                }
        );
    }

    public void ClearDB_OnClickButtonListener() {
        Button button_cleardb = (Button) findViewById(R.id.button_cleardb);
        button_cleardb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onClearDBClick();
                    }
                }
        );

    }


    @Override
    public String getUsername() {
        return username.getText().toString();
    }

    @Override
    public String getPassword(){
        return password.getText().toString();
    }

    @Override
    public void showUsernameError(int resId) {
        username.setError(getString(resId));
    }

    @Override
    public void showPasswordError(int resId){
        password.setError(getString(resId));
    }

    @Override
    public void startHomeActivity() {
        //TODO: implement caregiverTO and transfer to HomeActivity1
        //CaregiverTO caregiver = caregiverDAO.retrieve_Caregiver(username);
        Intent intent = new Intent("lehigh.cse.memcare.home.HomeActivity");
        intent.putExtra("first_name", "Username"/*caregiver.get_firstname()*/);
        startActivity(intent);
    }

    @Override
    public void showLoginError(int resId) {
        Toast.makeText(LoginActivity.this, "Login Failure.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showClearDbMessage() {
        Toast.makeText(LoginActivity.this, "Cleared DataBase", Toast.LENGTH_LONG).show();
    }

    @Override
    public void startRegistrationActvity() {
        Intent intent = new Intent("lehigh.cse.memcare.registration.user_registration.RegistrationActivity");
        startActivity(intent);
    }
}
