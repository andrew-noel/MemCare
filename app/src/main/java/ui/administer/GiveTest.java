package ui.administer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import lehigh.cse.memcare.R;
import midtier.DAOs.TestDAO;

public class GiveTest extends AppCompatActivity {


    Spinner spinner_availableTests;
    List<String> availableTests;

    TestDAO testDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_test);
        testDAO = new TestDAO();
        spinner_availableTests = (Spinner)(findViewById(R.id.spinner_availableTests));
        loadAvailableTests();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_give_test, menu);
        return true;
    }


    private void loadAvailableTests() {
        availableTests = testDAO.getListOfAvailableTestNames();
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
