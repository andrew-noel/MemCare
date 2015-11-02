package ui.administer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Set;

import lehigh.cse.memcare.R;
import midtier.DAOs.TestDAO;

public class adminster_takeTest extends AppCompatActivity {


    TestDAO dao;

    String testName;
    TextView textView_header;
    TextView textView_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminster_take_test);
        Bundle b = getIntent().getExtras();
        testName = b.getString("testName");
        dao = new TestDAO();

        textView_header = (TextView)findViewById(R.id.textView_header);
        textView_results = (TextView)findViewById(R.id.textView_results);
        textView_header.setText(testName);
        HashMap<String,String> questions = dao.getQuestionsHashTable(testName);
        Set<String> imageURIs = questions.keySet();

        for (String x : imageURIs){
            String temp = "";
            temp += questions.get(x) + "\n";
            temp += x;

            textView_results.append("\n" + temp + "\n");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adminster_take_test, menu);
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
