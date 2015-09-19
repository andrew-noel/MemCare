package registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.LoginService;
import database.RegistrationService;
import lehigh.cse.memcare.R;

public class Registration extends AppCompatActivity {

    RegistrationService registrationService;
    EditText editText_firstName, editText_lastName, editText_username, editText_password;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registrationService = new RegistrationService(this);

        editText_firstName = (EditText)findViewById(R.id.editText_firstname);
        editText_lastName = (EditText)findViewById(R.id.editText_lastname);
        editText_username = (EditText)findViewById(R.id.editText_username);
        editText_password = (EditText)findViewById(R.id.editText_password);
        btnRegister = (Button)findViewById(R.id.button_register);
        Register_OnClickButtonListener();

    }

    public void Register_OnClickButtonListener() {
        btnRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (LoginService.check_if_username_exists(editText_username.getText().toString().trim())) {
                            Toast.makeText(Registration.this, "Username already exists. Please try again.", Toast.LENGTH_LONG).show();

                        } else {
                            boolean isInserted = registrationService.insertData_registerCaregiver(editText_firstName.getText().toString().trim(),
                                    editText_lastName.getText().toString().trim(),
                                    editText_username.getText().toString().trim(),
                                    editText_password.getText().toString().trim());
                            if (isInserted) {
                                Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                //Intent intent = new Intent("lehigh.cse.memcare.MAIN");
                                //startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Registration.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
        );
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
