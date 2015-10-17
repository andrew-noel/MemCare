package ui.construction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import lehigh.cse.memcare.R;

public class ConstructionActivity extends AppCompatActivity {

    TextView textView_header, textView_selectPatient, textView_testType, textView_testName, textView_insertDate;
    Spinner spinner_patientList, spinner_testType;
    EditText editText_testName;
    private String[] tests, patients;
    Button createTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction);

        textView_header = (TextView)(findViewById(R.id.textView_createTestHeader));
        textView_selectPatient = (TextView)(findViewById(R.id.textView_selectPatient));
        textView_testType = (TextView)(findViewById(R.id.textView_testType));
        textView_testName = (TextView)(findViewById(R.id.textView_testName));
        textView_insertDate = (TextView)(findViewById(R.id.textView_insertDate));
        spinner_patientList = (Spinner)(findViewById(R.id.spinner_patientList));
        spinner_testType = (Spinner)(findViewById(R.id.spinner_testType));
        editText_testName = (EditText)(findViewById(R.id.editText_testName));

        tests = getResources().getStringArray(R.array.test_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tests);

        spinner_testType.setAdapter(adapter);

        Calendar currentDate = Calendar.getInstance();
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentYear = currentDate.get(Calendar.YEAR);

        textView_insertDate.append(" " + currentMonth + "/" + currentDay + "/" + currentYear);

        //TODO: Finish this construction class. Almost done, waiting on the patient name dropdown.

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
