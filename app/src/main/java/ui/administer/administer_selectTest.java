package ui.administer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import java.util.List;

import lehigh.cse.memcare.R;
import midtier.DAOs.CaregiverDAO;
import midtier.DAOs.TestDAO;
import midtier.models.userModel;
import midtier.services.testCreationService;

public class administer_selectTest extends AppCompatActivity {


    Spinner spinner_availableTests;
    List<String> availableTests;

    TestDAO testDAO;

    Button button_beginTest;
    testCreationService testCreationService;
    CaregiverDAO caregiverDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_test);
        testCreationService = new testCreationService(this);
        caregiverDAO = new CaregiverDAO();
        //testCreationService.dropTable();



        button_beginTest = (Button)findViewById(R.id.button_seeResults);
        StartTest_OnClickButtonListener();

        testDAO = new TestDAO();
        spinner_availableTests = (Spinner)(findViewById(R.id.spinner_patients));
        loadAvailableTests();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_give_test, menu);
        return true;
    }

    public void StartTest_OnClickButtonListener() {
        button_beginTest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(administer_selectTest.this, adminster_takeTest.class);
                        Bundle b = new Bundle();
                        String testName = spinner_availableTests.getSelectedItem().toString();
                        b.putString("testName", testName);
                        i.putExtras(b);

                        //Intent intent = new Intent("lehigh.cse.memcare.registration.takeTest");
                        startActivity(i);
                        finish();
                        //Toast.makeText(ConstructionActivity.this, "Cleared DB", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    private void loadAvailableTests() {
        availableTests = caregiverDAO.getTestForUser(userModel.getInstance().getUsername());

      //  availableTests = testDAO.getListOfAvailableTestNames();
        ArrayAdapter<String> testAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, availableTests);
        spinner_availableTests.setAdapter(testAdapter);

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
