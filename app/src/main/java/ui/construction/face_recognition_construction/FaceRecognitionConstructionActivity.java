package ui.construction.face_recognition_construction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dropbox.chooser.android.DbxChooser;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.TokenPair;

import lehigh.cse.memcare.R;

public class FaceRecognitionConstructionActivity extends AppCompatActivity {

    Button button_browse;
    Button button_add;
    EditText editText_name;

    ImageView imageView_photo;


    private DropboxAPI<AndroidAuthSession> dropboxApi;

    final static private String DROPBOX_FILE_DIR = "/";
    final static private String DROPBOX_NAME = "dropbox_prefs";

    final static private String APP_KEY = "mp95yxmhtyz7d5d";
    final static private String APP_SECRET = "hop2bk0uj5b2r0d";
    final static private Session.AccessType ACCESS_TYPE = Session.AccessType.DROPBOX;

    private DbxChooser mChooser;
    static final int DBX_CHOOSER_REQUEST = 0;

    Intent intent = getIntent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition_construction);

        button_browse = (Button)findViewById(R.id.button_browse);
        button_add = (Button)findViewById(R.id.button_addPhoto);
        editText_name = (EditText)findViewById(R.id.editText_photo_name);
        imageView_photo = (ImageView)findViewById(R.id.imageView_photo);

        mChooser = new DbxChooser(APP_KEY);
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

        chooseImage_OnClickButtonListener();;

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

            }catch(IllegalStateException e){
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DBX_CHOOSER_REQUEST) if (resultCode == Activity.RESULT_OK) {
            DbxChooser.Result result = new DbxChooser.Result(data);

            Uri image = result.getLink();

            imageView_photo.setImageURI(image);
            // Handle the result
        } else {
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
}
