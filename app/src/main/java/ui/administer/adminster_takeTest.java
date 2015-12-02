package ui.administer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.chooser.android.DbxChooser;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import lehigh.cse.memcare.R;
import midtier.DAOs.TestDAO;
import ui.construction.face_recognition_construction.FaceRecognitionConstructionView;
import ui.registration.patient_registration.RegisterPatientPresenter;

public class adminster_takeTest extends AppCompatActivity{


    TextToSpeech t1;

    TestDAO dao;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    Uri image_path;

    private Bitmap tempBitmap;
    private Canvas tempCanvas;
    private Paint myRectPaint;

    int faceID;
    SparseArray<Face> faces;
    Drawable photoDrawable;
    Rect imageBounds;
    Region imageRegion;

    String testName;
    TextView textView_header;
    EditText editText_inputName;
    Button button_next;
    Button button_mic;
    ImageView imageView_faceImage;

    static int counter = 0;
    static int numCorrect = 0;
    static int numWrong = 0;

    static List<String> imageURIs;
    HashMap<String,String> questions;
    HashMap<String, Integer> faceIndices;

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
        button_mic = (Button)findViewById(R.id.mic);
        imageView_faceImage = (ImageView)findViewById(R.id.imageView_faceImage);

        counter = 0;
        numCorrect = 0;
        numWrong = 0;

        textView_header.setText(testName);

        questions = dao.getQuestionsHashTable(testName);
        faceIndices = dao.getFaceIndexHashMap(testName);

        imageURIs = new ArrayList<>();
        imageURIs.addAll(questions.keySet());


        imageView_faceImage.setImageURI(Uri.parse(imageURIs.get(0)));
        image_path = Uri.parse(imageURIs.get(0));
        drawFaces(faceIndices.get(image_path.toString()) + 1);

        nextImage_OnClickButtonListener();

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Sansation-LightItalic.ttf");
        Typeface glyphicons = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        button_mic.setText("");
        button_mic.setTypeface(glyphicons);
        button_mic.setText("\uf130");

        button_mic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }


    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "SAY THE NAME");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "NOT SUPPORTED",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editText_inputName.setText(result.get(0));
                }
                break;
            }

        }
    }

    public void drawFaces(int faceId) {
        //TODO: // pull out logic to presenter.
        if (faceId == -1) return;


        try {
                InputStream inputStream = getContentResolver().openInputStream(image_path);
                photoDrawable = Drawable.createFromStream(inputStream, image_path.toString());
            } catch (FileNotFoundException e) {
                photoDrawable = getResources().getDrawable(R.drawable.brain_pic);
            }

            imageBounds = photoDrawable.getBounds();
            imageRegion = new Region(imageBounds.left, imageBounds.top, imageBounds.right, imageBounds.bottom);



            Bitmap myBitmap = null;
            try {
                myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            myRectPaint = new Paint();
            myRectPaint.setStrokeWidth(5);
            myRectPaint.setColor(Color.RED);
            myRectPaint.setStyle(Paint.Style.STROKE);

            tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), myBitmap.getConfig());
            tempCanvas = new Canvas(tempBitmap);
            tempCanvas.drawBitmap(myBitmap, 0, 0, null);

            FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).build();
            if (!faceDetector.isOperational()) {
                return;
            }

            Frame frame = new Frame.Builder().setBitmap(tempBitmap).build();
            faces = faceDetector.detect(frame);

            Face thisFace = faces.valueAt(faceId-1);
            float x1 = thisFace.getPosition().x;
            float y1 = thisFace.getPosition().y;
            float x2 = x1 + thisFace.getWidth();
            float y2 = y1 + thisFace.getHeight();

            tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);


            imageView_faceImage.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
            faceDetector.release();
        }



    public boolean isCorrect(String uri, String input, HashMap<String, String> lookupTable){
        String answer = lookupTable.get(uri);
        if (input.equalsIgnoreCase(answer)){
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
                        finish();
                        Intent intent = new Intent("lehigh.cse.memcare.home.HomeActivity");
                        startActivity(intent);

                    }
                })
               /* .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })*/
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
                            if (counter + 1 <= imageURIs.size()){
                                counter++;
                            }
                            //counter++;
                            if (counter < imageURIs.size()) {
                                imageView_faceImage.setImageURI(Uri.parse(imageURIs.get(counter)));
                                image_path = Uri.parse(imageURIs.get(counter));
                                drawFaces(faceIndices.get(image_path.toString())+1);
                            }

                        }else {

                            if (isCorrect(imageURIs.get(counter), editText_inputName.getText().toString(), questions)){
                                numCorrect++;
                            } else {
                                numWrong++;
                            }

                            showDialogMessage("Congratulations, you have completed the test.\n" +
                                    "Number Correct: " + numCorrect + "\n" +
                                    "Number Wrong: " + numWrong);


                        }
                        editText_inputName.setText("");
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
