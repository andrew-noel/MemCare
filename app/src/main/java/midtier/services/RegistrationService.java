package midtier.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import midtier.DatabaseHelper;

import static common.table_constants.*;

/**
 * Created by andrewmcmullen on 9/19/15.
 */
public class RegistrationService extends DatabaseHelper {

    public RegistrationService(Context context){
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    public boolean username_exists(String username){
        //db.execSQL("create table " + PATIENT_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, AGE TEXT, GENDER TEXT, CAREGIVER TEXT )");
        res = db.rawQuery("select * from " + CAREGIVER_TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        return DatabaseHelper.res.getCount() != 0;
    }

    public boolean patient_exists(String patient_firstname, String patient_lastname){
        //onCreate(db);
        res = db.rawQuery("select * from " + PATIENT_TABLE_NAME + " where FIRST_NAME = '" + patient_firstname + "' and LAST_NAME = '" + patient_lastname + "'", null);
        return DatabaseHelper.res.getCount() != 0;
    }

    public boolean insertData_registerCaregiver(String first_name, String last_name, String username, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CG_COLUMN_FIRST_NAME, first_name);
        contentValues.put(CG_COLUMN_LAST_NAME, last_name);
        contentValues.put(CG_COLUMN_USERNAME, username);
        contentValues.put(CG_COLUMN_PASSWORD, password);
        long result = db.insert(CAREGIVER_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean insertData_registerPatient(String first_name, String last_name, String age, String gender, String caregiver){

        ContentValues contentValues = new ContentValues();
        contentValues.put(P_COLUMN_FIRSTNAME, first_name);
        contentValues.put(P_COLUMN_LASSTNAME, last_name);
        contentValues.put(P_COLUMN_AGE, age);
        contentValues.put(P_COLUMN_GENDER, gender);
        contentValues.put(P_COLUMN_CAREGIVER, caregiver);
        long result = db.insert(PATIENT_TABLE_NAME, null, contentValues);
        return result != -1;

    }
}
