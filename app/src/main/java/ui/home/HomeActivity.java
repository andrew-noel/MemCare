package ui.home;

import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.TokenPair;

import java.util.ArrayList;

import lehigh.cse.memcare.R;

public class HomeActivity extends AppCompatActivity {

    private DropboxAPI<AndroidAuthSession> dropboxApi;

    final static private String DROPBOX_FILE_DIR = "/";
    final static private String DROPBOX_NAME = "dropbox_prefs";

    final static private String APP_KEY = "mp95yxmhtyz7d5d";
    final static private String APP_SECRET = "hop2bk0uj5b2r0d";
    final static private Session.AccessType ACCESS_TYPE = Session.AccessType.DROPBOX;

    private Button button_connectToDropBox;
    private LinearLayout container;

    // In the class declaration section:
    //private DropboxAPI<AndroidAuthSession> mDBApi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        button_connectToDropBox = (Button)findViewById(R.id.button_dropbox);
        container = (LinearLayout) findViewById(R.id.container);

        TextView textView = (TextView)findViewById(R.id.textView_welcome);
        Intent intent = getIntent();
        String users_name = intent.getStringExtra("first_name");
        textView.append(" " + users_name);

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
        Connect_OnClickButtonListener();
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
    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            ArrayList<String> result = message.getData().getStringArrayList("data");

            for (String fileName : result){
                TextView textView = new TextView(HomeActivity.this);
                textView.setText(fileName);
                container.addView(textView);
            }
        }
    };
    public void Connect_OnClickButtonListener() {
        button_connectToDropBox.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dropboxApi.getSession().startAuthentication(HomeActivity.this);
                        ListFiles listFiles = new ListFiles(dropboxApi, DROPBOX_FILE_DIR, handler);
                        listFiles.execute();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
