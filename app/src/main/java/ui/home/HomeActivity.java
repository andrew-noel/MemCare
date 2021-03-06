package ui.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import lehigh.cse.memcare.R;
import midtier.DAOs.CaregiverDAO;
import midtier.DatabaseHelper;
import midtier.DatabaseHelperInt;
import midtier.models.userModel;

//TODO: refactor to MVP
public class HomeActivity extends AppCompatActivity {

    Button button_create_test;
    Button button_logout;
    Button button_settings;
    Button button_give_test;
    Button button_viewPastTests;
    CaregiverDAO caregiverDAO;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        button_create_test = (Button) findViewById(R.id.button_create_test);
        button_logout = (Button) findViewById(R.id.button_logout);
        button_settings = (Button) findViewById(R.id.button_settings);
        button_give_test = (Button)findViewById(R.id.button_give_test);
        button_viewPastTests = (Button)findViewById(R.id.button_view_past_tests);
        ConstructTest_OnClickButtonListener();
        Settings_OnClickButtonListener();
        Logout_OnClickButtonListener();
        ViewPastTests_OnClickButtonListener();
        GiveTest_OnClickButtonListener();
        caregiverDAO = new CaregiverDAO();
        databaseHelper = new DatabaseHelper(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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

    public void ViewPastTests_OnClickButtonListener(){
        button_viewPastTests.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        //TODO: this is a temporary placement for the construction activity. I plan on moving it when it's up and running.
                        Intent intent = new Intent ("lehigh.cse.memcare.ui.Select_Patient_For_Results");
                        //Intent intent = new Intent ("ui.Results.ViewPastResults");
                        startActivity(intent);
                    }
                }
        );

    }

    public void GiveTest_OnClickButtonListener(){
        button_give_test.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                public void onClick(View v){
                        //TODO: this is a temporary placement for the construction activity. I plan on moving it when it's up and running.
                        if (userModel.getInstance().hasTests()) {
                            Intent intent = new Intent("lehigh.cse.memcare.registration.selectTest");
                            startActivity(intent);
                        } else {
                            Toast.makeText(HomeActivity.this, "Please create a test first", Toast.LENGTH_LONG).show();

                        }
                    }
                }
        );

    }

    public void ConstructTest_OnClickButtonListener() {
        button_create_test.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: start intent to ConstructionActivity where you can create different kinds of tests
                        String username = userModel.getInstance().getUsername();
                        if (!caregiverDAO.getListOfPatientNames(username).isEmpty()) {
                            Intent intent = new Intent("lehigh.cse.memcare.construction.ConstructionActivity");
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(HomeActivity.this, "Please register patient before creating test", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void Logout_OnClickButtonListener() {
        button_logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: fix login intent
                        //Intent intent = new Intent("android.intent.action.MAIN");
                        finish();
                        //Intent intent = new Intent("lehigh.cse.memcare.login.LoginActivity");
                        //startActivity(intent);
                    }
                }
        );
    }

    public void Settings_OnClickButtonListener() {
        button_settings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //FIXME: temporarily goes to patient registration
                        Intent intent = new Intent("lehigh.cse.memcare.registration.RegisterPatientActivity");
                        startActivity(intent);
                    }
                }
        );
    }

}