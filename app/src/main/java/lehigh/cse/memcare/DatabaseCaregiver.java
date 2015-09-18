package lehigh.cse.memcare;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

/**
 * Created by Noel on 9/18/15.
 */



public class DatabaseCaregiver extends DatabaseHelper{

    public DatabaseCaregiver(Context context){
        super(context);

    }




    public boolean insertData_registerCaregiver(String first_name, String last_name, String username, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2, first_name);
        contentValues.put(COLUMN_3, last_name);
        contentValues.put(COLUMN_4, username);
        contentValues.put(COLUMN_5, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean check_if_username_exists(String username){
        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        return res.getCount() != 0;
    }

    public boolean checkPassword(String username, String password){
        if (!check_if_username_exists(username)){
            return false;
        }

        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);

        res.moveToNext();

        String db_password = res.getString(INDEX_PASSWORD);

        //Log.d("PASSWORD", "PASSWORD = " + db_password);
        return password.equals(db_password);


    }

    public String get_first_Name(String username, String password){

        if (!checkPassword(username, password)){
            return null;
        }

        String first_name;
        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        first_name = res.getString(INDEX_FIRST_NAME);
        Log.d("PASSWORD", "FIRST_NAME = " + first_name);
        return first_name;

    }



}
