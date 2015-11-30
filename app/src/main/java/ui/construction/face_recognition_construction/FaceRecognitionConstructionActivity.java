package ui.construction.face_recognition_construction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.chooser.android.DbxChooser;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.TokenPair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import lehigh.cse.memcare.R;
import midtier.services.testCreationService;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.LinearLayout.*;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;


public class FaceRecognitionConstructionActivity extends AppCompatActivity implements FaceRecognitionConstructionView, OnTouchListener {


    testCreationService service;

    Button button_browse;
    Button button_add;
    Button button_finishActivity;
    EditText editText_name;

    TextView header;

    Uri image_path;
    ImageView imageView_photo;

    private Bitmap tempBitmap;
    private Canvas tempCanvas;
    private Paint myRectPaint;

    private DropboxAPI<AndroidAuthSession> dropboxApi;

    //TODO: pull out dropbox stuff to seperate file
    //TODO: should not have to authenticate dropbox account every you create a test.

    final static private String DROPBOX_FILE_DIR = "/";
    final static private String DROPBOX_NAME = "dropbox_prefs";

    final static private String APP_KEY = "mp95yxmhtyz7d5d";
    final static private String APP_SECRET = "hop2bk0uj5b2r0d";
    final static private Session.AccessType ACCESS_TYPE = Session.AccessType.DROPBOX;

    private DbxChooser mChooser;
    static final int DBX_CHOOSER_REQUEST = 0;


    Intent intent = getIntent();

    String testName;
    int faceID = -2;
    SparseArray<Face> faces;
    Drawable photoDrawable;
    Rect imageBounds;
    Region imageRegion;



    @Override
    public void AuthenticateDropBox() {
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session;

        SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
        String key = prefs.getString(APP_KEY, null);
        String secret = prefs.getString(APP_SECRET, null);

        if (key != null && secret != null) {
            AccessTokenPair token = new AccessTokenPair(key, secret);
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE, token);
        } else {
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE);
        }

        dropboxApi = new DropboxAPI<AndroidAuthSession>(session);
        dropboxApi.getSession().startAuthentication(FaceRecognitionConstructionActivity.this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition_construction);

        Bundle b = getIntent().getExtras();
        testName = b.getString("testName");

        service = new testCreationService(this);

        header = (TextView) findViewById(R.id.textView_header);
        button_browse = (Button) findViewById(R.id.button_browse);
        button_add = (Button) findViewById(R.id.button_addPhoto);
        editText_name = (EditText) findViewById(R.id.editText_photo_name);
        imageView_photo = (ImageView) findViewById(R.id.imageView_photo);
        button_finishActivity = (Button) findViewById(R.id.button_finish_creation);

        finishActivity_OnClickButtonListener();
        addPhoto_OnClickButtonListener();
        mChooser = new DbxChooser(APP_KEY);
        AuthenticateDropBox();
        chooseImage_OnClickButtonListener();

        header.setText(testName);


    }

    @Override
    protected void onResume() {
        super.onResume();

        AndroidAuthSession session = dropboxApi.getSession();
        if (session.authenticationSuccessful()) {
            try {
                session.finishAuthentication();

                TokenPair tokens = session.getAccessTokenPair();
                SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(APP_KEY, tokens.key);
                editor.putString(APP_SECRET, tokens.secret);
                editor.commit();

            } catch (IllegalStateException e) {
                Toast.makeText(this, "Error during Dropbox authentication", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void chooseImage_OnClickButtonListener() {
        button_browse.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mChooser.forResultType(DbxChooser.ResultType.FILE_CONTENT)
                                .launch(FaceRecognitionConstructionActivity.this, DBX_CHOOSER_REQUEST);
                    }
                }
        );
    }

    public void addPhoto_OnClickButtonListener() {
        button_add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: add photo
                        String name = editText_name.getText().toString();
                        int faceId = faceID;
                        service.insertData_addQuestion(testName, image_path.toString(), name, "" + faceID + "");
                        Toast.makeText(FaceRecognitionConstructionActivity.this, "Photo Succesfully inserted into DB", Toast.LENGTH_LONG).show();
                        faceID = -2;

                    }
                }
        );
    }

    public void finishActivity_OnClickButtonListener(){
        button_finishActivity.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    finish();
                        Intent intent = new Intent ("lehigh.cse.memcare.home.HomeActivity");
                        startActivity(intent);

                    }
                }
        );

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO: // pull out logic to presenter.
        if (requestCode == DBX_CHOOSER_REQUEST) if (resultCode == Activity.RESULT_OK) {
            DbxChooser.Result result = new DbxChooser.Result(data);
            image_path = result.getLink();
            setPhoto(image_path);

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
            myRectPaint.setColor(Color.WHITE);
            myRectPaint.setStyle(Paint.Style.STROKE);

            tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), myBitmap.getConfig());
            tempCanvas = new Canvas(tempBitmap);
            tempCanvas.drawBitmap(myBitmap, 0, 0, null);
            imageView_photo.setOnTouchListener(this);

            FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).build();
            if (!faceDetector.isOperational()) {
                return;
            }

            Frame frame = new Frame.Builder().setBitmap(tempBitmap).build();
            faces = faceDetector.detect(frame);


            for (int i = 0; i < faces.size(); i++) {
                Face thisFace = faces.valueAt(i);
                float x1 = thisFace.getPosition().x;
                float y1 = thisFace.getPosition().y;
                float x2 = x1 + thisFace.getWidth();
                float y2 = y1 + thisFace.getHeight();

                tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
            }

            imageView_photo.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
            faceDetector.release();
        }

         else {
            // Failed or was cancelled by the user.
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_face_recognition_construction, menu);
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

    @Override
    public void setPhoto(Uri image_path) {
        imageView_photo.setImageURI(image_path);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId() == R.id.imageView_photo){

            if(image_path != null) {
                faceID = -2;

                int[] viewCoords = new int[2];
                imageView_photo.getLocationOnScreen(viewCoords);

                // calculate inverse matrix
                Matrix inverse = new Matrix();
                imageView_photo.getImageMatrix().invert(inverse);

                // map touch point from ImageView to image
                float[] touchPoint = new float[] {event.getX(), event.getY()};
                inverse.mapPoints(touchPoint);


                float touchX = touchPoint[0];
                float touchY = touchPoint[1];


                myRectPaint = new Paint();
                myRectPaint.setStrokeWidth(5);
                myRectPaint.setColor(Color.WHITE);
                myRectPaint.setStyle(Paint.Style.STROKE);


                for (int i = 0; i < faces.size(); i++) {
                    Face thisFace = faces.valueAt(i);
                    float x1 = thisFace.getPosition().x;
                    float y1 = thisFace.getPosition().y;
                    float x2 = x1 + thisFace.getWidth();
                    float y2 = y1 + thisFace.getHeight();

                    tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
                }

                myRectPaint.setColor(Color.RED);

                for (int i = 0; i < faces.size(); i++) {
                    Face thisFace = faces.valueAt(i);

                    float x1 = thisFace.getPosition().x;
                    float y1 = thisFace.getPosition().y;
                    float x2 = x1 + thisFace.getWidth();
                    float y2 = y1 + thisFace.getHeight();

                    if(touchX > x1  && touchX < x2 && touchY > y1 && touchY < y2){
                        faceID = i;
                        tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
                        break;
                    }
                }
                imageView_photo.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));

            }

        }

        return true;
    }
}
