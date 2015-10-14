package ui.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import lehigh.cse.memcare.R;

public class HomeActivity extends AppCompatActivity {

    Button button_create_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        button_create_test = (Button) findViewById(R.id.button_create_test);
        CreateTest_OnClickButtonListener();
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

    public void CreateTest_OnClickButtonListener(){

        button_create_test.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: start intent to ConstructionActivity where you can create different kinds of tests
                        Intent intent = new Intent("lehigh.cse.memcare.construction.FaceRecognitionConstructionActivity");
                        startActivity(intent);
                    }
                }
        );
    }
}
