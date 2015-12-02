package ui.registration.user_registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import midtier.services.RegistrationService;
import lehigh.cse.memcare.R;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView{

    RegistrationService service;
    EditText editText_firstName, editText_lastName, editText_username, editText_password, editText_confirmpassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        service = new RegistrationService(this);
        //service.create();
        editText_firstName = (EditText)findViewById(R.id.editText_patientfirstname);
        editText_lastName = (EditText)findViewById(R.id.editText_patientlastname);
        editText_username = (EditText)findViewById(R.id.editText_username);
        editText_password = (EditText)findViewById(R.id.editText_password);
        editText_confirmpassword = (EditText)findViewById(R.id.editText_confirmpassword);
        btnRegister = (Button)findViewById(R.id.button_patientregister);
        Register_OnClickButtonListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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

    public void Register_OnClickButtonListener() {
        final RegistrationPresenter presenter = new RegistrationPresenter(this, service);
        btnRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onLoginClicked();
                    }

                }
        );
    }


    @Override
    public String getUsername() {
        return editText_username.getText().toString();
    }

    @Override
    public String getLastName() {
        return editText_lastName.getText().toString();
    }

    @Override
    public String getFirstName() {
        return editText_firstName.getText().toString();
    }

    @Override
    public String getPassword() {
        return editText_password.getText().toString();
    }

    @Override
    public String getConfirmPassword(){ return editText_confirmpassword.getText().toString(); }

    @Override
    public void showUsernameError(int username_error) {
        editText_username.setError(getString(username_error));
    }

    @Override
    public void showFirstNameError(int registration_firstname_error) {
        editText_firstName.setError(getString(registration_firstname_error));
    }

    @Override
    public void showLastNameError(int lastname_error) {
        editText_lastName.setError(getString(lastname_error));
    }

    @Override
    public void showPasswordError(int registration_password_error) {
        editText_password.setError(getString(registration_password_error));
    }

    @Override
    public void showConfirmPasswordError(int registration_confirm_password_error) {
        editText_confirmpassword.setError(getString(registration_confirm_password_error));
    }

    @Override
    public void showConfirmPasswordMismatch(int registration_password_mismatch) {
        editText_confirmpassword.setError(getString(registration_password_mismatch));
    }

    @Override
    public void showUsernameAlreadyExistsError(int registration_userAlreadyExists_error) {
        editText_username.setError(getString(registration_userAlreadyExists_error));
    }

    @Override
    public void showRegistrationError() {
        Toast.makeText(RegistrationActivity.this, "Registration Failure.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void returnToLoginActivity() {
       finish();
    }
}
