package ui.construction.face_recognition_construction_init;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import lehigh.cse.memcare.R;
import midtier.DAOs.PatientDAOInt;
import midtier.DAOs.PatientDAO;
import midtier.services.testCreationService;
import ui.construction.face_recognition_construction.FaceRecognitionConstructionActivity;

public class ConstructionActivity extends AppCompatActivity {

    //TextView textView_header, textView_selectPatient, textView_testType, textView_testName, textView_insertDate;
    TextView textView_insertDate;

    Spinner spinner_patientList, spinner_testType;

    EditText editText_testName;

    private String[] tests, patients;

    Button button_createTest;
    Button button_clearDB;

    List<String> patientNames;

    PatientDAOInt patientDAO;
    testCreationService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction);

        service = new testCreationService(this);

        textView_insertDate = (TextView)(findViewById(R.id.textView_insertDate));
        spinner_patientList = (Spinner)(findViewById(R.id.spinner_patientList));
        spinner_testType = (Spinner)(findViewById(R.id.spinner_testType));
        editText_testName = (EditText)(findViewById(R.id.editText_testName));
        button_createTest = (Button)(findViewById(R.id.button_createTest));
        button_clearDB = (Button)(findViewById(R.id.button_clearDB));
        patientDAO = new PatientDAO();

        tests = getResources().getStringArray(R.array.test_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tests);
        spinner_testType.setAdapter(adapter);

        loadPatients();

        textView_insertDate.append(getCurrentDate());
        createTest_OnClickButtonListener();
        clearDB_OnClickButtonListener();

        //TODO: All the elements are here now. Adding functionality to these elements soon. Also need to create Presenter & View

    }

    private String getCurrentDate(){
        Calendar currentDate = Calendar.getInstance();
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        return " " + currentMonth + "/" + currentDay + "/" + currentYear;
    }

    private void loadPatients() {
        patientNames = patientDAO.getListOfPatientNames();
        ArrayAdapter<String> patientAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, patientNames);
        spinner_patientList.setAdapter(patientAdapter);
    }

    public void createTest_OnClickButtonListener() {
        button_createTest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: refactor to presenter, get real owner
                        String owner = "Andrew McMullen"; //FIXME: temporary
                        String patient_full_name = spinner_patientList.getSelectedItem().toString();
                        String testName = editText_testName.getText().toString();
                        String testType = spinner_testType.getSelectedItem().toString();
                        String dateOfCreation = getCurrentDate();

                        service.insertData_createTest(owner, patient_full_name, testName, testType, dateOfCreation);
                        Intent intent = new Intent("lehigh.cse.memcare.construction.FaceRecognitionConstructionActivity");



                        Intent i = new Intent(ConstructionActivity.this, FaceRecognitionConstructionActivity.class);
                        Bundle b = new Bundle();
                        b.putString("testName", testName);
                        i.putExtras(b);

                        Toast.makeText(ConstructionActivity.this, "Successfuly inserted into DB", Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }
                }
        );
    }

    public void clearDB_OnClickButtonListener() {
        button_clearDB.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        service.dropTable();
                        Toast.makeText(ConstructionActivity.this, "Cleared DB", Toast.LENGTH_LONG).show();
                    }
                }
        );
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
