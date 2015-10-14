package midtier.registration;

import android.content.ContentValues;
import android.content.Context;

import midtier.DatabaseHelperImpl;

import static common.CaregiverTableConstants.*;
import static common.PatientTableConstants.*;

/**
 * Created by andrewmcmullen on 9/19/15.
 */
public class RegistrationService extends DatabaseHelperImpl {

    public RegistrationService(Context context){
        super(context);
    }

    public boolean username_exists(String username){
        res = db.rawQuery("select * from " + CAREGIVER_TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        return DatabaseHelperImpl.res.getCount() != 0;
    }

    public boolean patient_exists(String patient){
        res = db.rawQuery("select * from " + PATIENT_TABLE_NAME + " where FIRST_NAME = '" + patient + "'", null);
        return DatabaseHelperImpl.res.getCount() != 0;
    }

    public boolean insertData_registerCaregiver(String first_name, String last_name, String username, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2, first_name);
        contentValues.put(COLUMN_3, last_name);
        contentValues.put(COLUMN_4, username);
        contentValues.put(COLUMN_5, password);
        long result = db.insert(CAREGIVER_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean insertData_registerPatient(String first_name, String last_name, String age, String gender){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PATIENT_COLUMN_2, first_name);
        contentValues.put(PATIENT_COLUMN_3, last_name);
        contentValues.put(PATIENT_COLUMN_4, age);
        contentValues.put(PATIENT_COLUMN_5, gender);
        long result = db.insert(PATIENT_TABLE_NAME, null, contentValues);
        return result != -1;

    }
}
