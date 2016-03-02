package midtier.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import midtier.DatabaseHelper;

import static common.table_constants.*;

/**
 * Created by andrewmcmullen on 9/19/15.
 */
public class LoginService extends DatabaseHelper {

    public LoginService(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // db.execSQL("create table " + CAREGIVER_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, USER_NAME TEXT, PASSWORD TEXT )");
    }

    public boolean login(String username, String password){
        res = db.rawQuery("select * from " + CAREGIVER_TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        if (DatabaseHelper.res.getCount() == 0){
            return false;
        }
        DatabaseHelper.res.moveToNext();
        String db_password = DatabaseHelper.res.getString(4);
        //Log.d("PASSWORD", "PASSWORD = " + db_password);
        return password.equals(db_password);
    }
}
