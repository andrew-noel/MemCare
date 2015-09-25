package ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import midtier.TO.CaregiverTO;
//import midtier.registration.CaregiverDAOImpl;
import midtier.login.LoginService;
import lehigh.cse.memcare.R;

import java.lang.String;

public class LoginActivity extends AppCompatActivity implements LoginView {

    //TODO: implement LoginModel
    LoginService loginService;

    private static EditText username;
    private static EditText password;

    //private static Button button_register;
    //private static Button button_login;
    //private static Button button_cleardb;

    //CaregiverDAOImpl caregiverDAO;
    LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this, new LoginService(this));
        //CaregiverDAOImpl caregiverDAO = new CaregiverDAOImpl();

        //register buttons
        Registration_OnClickButtonListener();
        Login_OnClickButtonListener();
        Cleardb_OnClickButtonListener();

        //text handles
        username = (EditText) findViewById(R.id.editText_username);
        password = (EditText) findViewById(R.id.editText_password);

        //initialize values
        username.setText("");
        password.setText("");
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

    public void Registration_OnClickButtonListener() {
        Button button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: refactor to presenter
                        Intent intent = new Intent("lehigh.cse.memcare.registration.RegistrationActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    public void Cleardb_OnClickButtonListener() {
        Button button_cleardb = (Button) findViewById(R.id.button_cleardb);
        button_cleardb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: refactor to presenter
                        loginService.clear_database();
                        Toast.makeText(LoginActivity.this, "Cleared DataBase", Toast.LENGTH_LONG).show();
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
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        //TODO: implement caregiverTO and transfer to HomeActivity
        //CaregiverTO caregiver = caregiverDAO.retrieve_Caregiver(username);
        Intent intent = new Intent("lehigh.cse.memcare.HomeActivity");
        intent.putExtra("first_name", "Username"/*caregiver.get_firstname()*/);
        startActivity(intent);
    }

    @Override
    public void showLoginError(int resId) {
        Toast.makeText(LoginActivity.this, "Login Failure.", Toast.LENGTH_LONG).show();
    }
}
