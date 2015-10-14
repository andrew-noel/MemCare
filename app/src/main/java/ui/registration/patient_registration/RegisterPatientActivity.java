package ui.registration.patient_registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import lehigh.cse.memcare.R;

public class RegisterPatientActivity extends AppCompatActivity implements RegisterPatientView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);
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
        return null;
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public String getAge() {
        return null;
    }

    @Override
    public String getGender() {
        return null;
    }

    @Override
    public void showFirstnameError(int registration_firstname_error) {

    }

    @Override
    public void showLastnameError(int registration_lastname_error) {

    }

    @Override
    public void showAgeError(int registration_age_error) {

    }

    @Override
    public void showGenderError(int registration_gender_error) {

    }

    @Override
    public void showPatientAlreadyExistsError(int registration_patientAlreadyExists_error) {

    }

    @Override
    public void showRegistationError() {

    }

    @Override
    public void returnToTestActivity() {

    }
}
