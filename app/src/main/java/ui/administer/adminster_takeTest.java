package ui.administer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import lehigh.cse.memcare.R;
import midtier.DAOs.TestDAO;
import ui.registration.patient_registration.RegisterPatientPresenter;

public class adminster_takeTest extends AppCompatActivity {


    TestDAO dao;

    String testName;
    TextView textView_header;
    EditText editText_inputName;
    Button button_next;
    ImageView imageView_faceImage;

    static int counter = 0;
    static int numCorrect = 0;
    static int numWrong = 0;

    static List<String> imageURIs;
    HashMap<String,String> questions;

    //TextView textView_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminster_take_test);
        Bundle b = getIntent().getExtras();
        testName = b.getString("testName");
        dao = new TestDAO();

        textView_header = (TextView)findViewById(R.id.textView_header);
        editText_inputName = (EditText)findViewById(R.id.editText_inputName);
        button_next = (Button)findViewById(R.id.button_next);
        imageView_faceImage = (ImageView)findViewById(R.id.imageView_faceImage);

        counter = 0;
        numCorrect = 0;
        numWrong = 0;


        //textView_results = (TextView)findViewById(R.id.textView_results);
        textView_header.setText(testName);

        questions = dao.getQuestionsHashTable(testName);
        imageURIs = new ArrayList<String>();
        imageURIs.addAll(questions.keySet());
        //Set<String> imageURIs = questions.keySet();


        imageView_faceImage.setImageURI(Uri.parse(imageURIs.get(0)));

        nextImage_OnClickButtonListener();
        /*

        for (String x : imageURIs){
            String temp = "";
            temp += questions.get(x) + "\n";
            temp += x;

            textView_results.append("\n" + temp + "\n");
        }
        */


    }


    public boolean isCorrect(String uri, String input, HashMap<String, String> lookupTable){
        String answer = lookupTable.get(uri);
        if (input.equals(answer)){
            return true;
        }else {
            return false;
        }
    }


    public void showDialogMessage(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Test Complete")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }

    public void nextImage_OnClickButtonListener(){
        button_next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter < imageURIs.size()-1) {
                            //TODO: no input should provide warning, should NOT count as incorrect
                            if (isCorrect(imageURIs.get(counter), editText_inputName.getText().toString(), questions)){
                                numCorrect++;
                            } else {
                                numWrong++;
                            }
                            counter++;
                            if (counter < imageURIs.size()) {
                                imageView_faceImage.setImageURI(Uri.parse(imageURIs.get(counter)));
                            }
                        }else {

                            if (isCorrect(imageURIs.get(counter-1), editText_inputName.getText().toString(), questions)){
                                numCorrect++;
                            } else {
                                numWrong++;
                            }

                            showDialogMessage("Congradulations, you have complete the test.\n" +
                                    "Num Correct: " + numCorrect + "\n" +
                                    "Num Wrong: " + numWrong);
                        }
                    }

                }
        );

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
