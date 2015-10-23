package ui.construction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import lehigh.cse.memcare.R;
import midtier.registration.PatientDAO;
import midtier.registration.PatientDAOImpl;

public class ConstructionActivity extends AppCompatActivity {

    TextView textView_header, textView_selectPatient, textView_testType, textView_testName, textView_insertDate;
    Spinner spinner_patientList, spinner_testType;
    EditText editText_testName;
    private String[] tests, patients;
    Button createTest;
    List<String> patientNames;

    PatientDAOImpl patientDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction);

        textView_header = (TextView)(findViewById(R.id.textView_header));
        textView_selectPatient = (TextView)(findViewById(R.id.textView_selectPatient));
        textView_testType = (TextView)(findViewById(R.id.textView_testType));
        textView_testName = (TextView)(findViewById(R.id.textView_testName));
        textView_insertDate = (TextView)(findViewById(R.id.textView_insertDate));
        spinner_patientList = (Spinner)(findViewById(R.id.spinner_patientList));
        spinner_testType = (Spinner)(findViewById(R.id.spinner_testType));
        editText_testName = (EditText)(findViewById(R.id.editText_testName));
        patientDAO = new PatientDAOImpl();

        tests = getResources().getStringArray(R.array.test_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tests);



        spinner_testType.setAdapter(adapter);

        loadPatients();
        Calendar currentDate = Calendar.getInstance();
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentYear = currentDate.get(Calendar.YEAR);

        textView_insertDate.append(" " + currentMonth + "/" + currentDay + "/" + currentYear);

        //TODO: All the elements are here now. Adding functionality to these elements soon. Also need to create Presenter & View

    }

    private void loadPatients() {
        patientNames = patientDAO.getEveryPatientName();

        ArrayAdapter<String> patientAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, patientNames);

        spinner_patientList.setAdapter(patientAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_test, menu);
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
