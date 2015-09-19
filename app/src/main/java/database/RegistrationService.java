package database;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created by andrewmcmullen on 9/19/15.
 */
public class RegistrationService extends DatabaseHelperImpl{

    public RegistrationService(Context context){
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
}
