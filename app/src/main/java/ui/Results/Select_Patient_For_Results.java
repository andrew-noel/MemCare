package ui.Results;

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
import java.util.StringTokenizer;

import lehigh.cse.memcare.R;
import midtier.DAOs.CaregiverDAO;
import midtier.DAOs.TestDAO;
import midtier.models.userModel;
import midtier.services.testCreationService;
import ui.administer.adminster_takeTest;

public class Select_Patient_For_Results extends AppCompatActivity {

    Spinner spinner_availablePatients;
    List<String> availablePatients;



    Button button_seeResults;
    CaregiverDAO caregiverDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__patient__for__results);
        caregiverDAO = new CaregiverDAO();


        button_seeResults = (Button)findViewById(R.id.button_seeResults);
        seeResults_OnClickButtonListener();
        spinner_availablePatients = (Spinner)findViewById(R.id.spinner_patient);


        loadPatientNames();
    }


    public void seeResults_OnClickButtonListener() {
        button_seeResults.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(Select_Patient_For_Results.this, Anaylsis.class);
                        Bundle b = new Bundle();
                        String patientName = spinner_availablePatients.getSelectedItem().toString();
                        StringTokenizer tokenizer = new StringTokenizer(patientName, " ");
                        String fname = tokenizer.nextToken();
                        b.putString("patientName", fname);
                        i.putExtras(b);

                        //Intent intent = new Intent("lehigh.cse.memcare.registration.takeTest");
                        startActivity(i);
                        finish();
                        //Toast.makeText(ConstructionActivity.this, "Cleared DB", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select__patient__for__results, menu);
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



    private void loadPatientNames(){
        availablePatients = caregiverDAO.getListOfPatientNames(userModel.getInstance().getUsername());

        //  availableTests = testDAO.getListOfAvailableTestNames();
        ArrayAdapter<String> testAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, availablePatients);
        spinner_availablePatients.setAdapter(testAdapter);

    }
}
