package ui.registration.patient_registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import lehigh.cse.memcare.R;
import midtier.registration.RegistrationService;
import ui.registration.user_registration.RegistrationPresenter;

public class RegisterPatientActivity extends AppCompatActivity implements RegisterPatientView{

EditText editText_firstname, editText_lastname, editText_age;
    RadioGroup radioGroup_Gender;
    RadioButton radioButton_female, radioButton_male;
    Button button_register, button_clearDB;
    RegistrationService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = new RegistrationService(this);
        //service.delete_table("Patients_table");
        setContentView(R.layout.activity_register_patient);
        editText_firstname = (EditText)(findViewById(R.id.editText_patientfirstname));
        editText_lastname = (EditText)(findViewById(R.id.editText_patientlastname));
        editText_age = (EditText)(findViewById(R.id.editText_age));
        radioGroup_Gender = (RadioGroup)(findViewById(R.id.radioGroup_Gender));
        radioButton_female = (RadioButton)(findViewById(R.id.radioButton_female));
        radioButton_male = (RadioButton) (findViewById(R.id.radioButton_male));
        button_register = (Button)(findViewById(R.id.button_patientregister));
        button_clearDB = (Button)(findViewById(R.id.button_clearPatientDB));

        service = new RegistrationService(this);

        Register_OnClickButtonListener();
        ClearDB_OnClickButtonListener();



    }

    public void Register_OnClickButtonListener() {
        final RegisterPatientPresenter presenter = new RegisterPatientPresenter(this, service);
        button_register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onRegisterClicked();
                    }

                }
        );
    }
    public void ClearDB_OnClickButtonListener(){
        final RegisterPatientPresenter presenter = new RegisterPatientPresenter(this, service);
        button_clearDB.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onClearDBClicked();
                    }

                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_patient, menu);
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

    @Override
    public String getFirstName() {
        return editText_firstname.getText().toString();
    }

    @Override
    public String getLastName() {
        return editText_lastname.getText().toString();
    }

    @Override
    public String getAge() {
        return editText_age.getText().toString();
    }

    @Override
    public String getGender() {
        int id = radioGroup_Gender.getCheckedRadioButtonId();

        RadioButton sex = (RadioButton)(findViewById(id));

        if(sex == null) return "";
        return sex.getText().toString();
    }

    @Override
    public void showFirstnameError(int registration_firstname_error) {
        editText_firstname.setError(getString(registration_firstname_error));
    }

    @Override
    public void showLastnameError(int registration_lastname_error) {
        editText_lastname.setError(getString(registration_lastname_error));
    }

    @Override
    public void showAgeError(int registration_age_error) {
        editText_age.setError(getString(registration_age_error));
    }

    @Override
    public  void showGenderError(int registration_gender_error){
        radioButton_female.setError(getString(registration_gender_error));
        radioButton_male.setError(getString(registration_gender_error));
    }


    @Override
    public void showPatientAlreadyExistsError(int registration_patientAlreadyExists_error) {
        button_register.setError(getString(registration_patientAlreadyExists_error));
    }

    @Override
    public void showRegistationError() {
        button_register.setError("Registration Failed.");
    }

    @Override
    public void returnToTestActivity() {
        finish();
    }

    @Override
    public void showClearPatientDBMessage() {
        Toast.makeText(RegisterPatientActivity.this, "Cleared DataBase", Toast.LENGTH_LONG).show();
    }
}
