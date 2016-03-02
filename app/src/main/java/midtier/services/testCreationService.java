package midtier.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import midtier.DatabaseHelper;

import static common.table_constants.*;

/**
 * Created by andrewmcmullen on 11/1/15.
 */
public class testCreationService extends DatabaseHelper{

    public testCreationService(Context context){
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    public boolean insertData_createTest(String owner, String patient_full_name, String testName, String testType, String dateOfCreation) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(TT_COLUMN_CAREGIVER, owner);
        contentValues.put(TT_COLUMN_PATIENT, patient_full_name);
        contentValues.put(TT_COLUMN_TESTNAME, testName);
        contentValues.put(TT_COLUMN_TEST_TYPE, testType);
        contentValues.put(TT_COLUMN_DATE, dateOfCreation);
        long result = db.insert(TEST_CREATE_TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public boolean insertData_addQuestion(String testName, String URI, String person_name, String faceIndex){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Q_COLUMN_TESTNAME, testName);
        contentValues.put(Q_COLUMN_IMAGE_URI, URI);
        contentValues.put(Q_COLUMN_PERSON_NAME, person_name);
        contentValues.put(Q_COLUMN_FACE_INDEX, faceIndex);
        long result = db.insert(QUESTIONS_TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public boolean insertData_Results(String patient_firstname, String patient_lastname, String username, String testname, String score, String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put(R_COLUMN_PATIENT_FIRSTNAME, patient_firstname);
        contentValues.put(R_COLUMN_PATIENT_LASTNAME, patient_lastname);
        contentValues.put(R_COLUMN_CAREGIVER, username);
        contentValues.put(R_COLUMN_DATE, testname);
        contentValues.put(R_COLUMN_DATE, date);
        contentValues.put(R_COLUMN_SCORE, score);
        long result = db.insert(RESULTS_TABLE_NAME, null, contentValues);

        return result != -1;

    }


    public void dropTable(){
        this.delete_table(TEST_CREATE_TABLE_NAME);
        this.delete_table(QUESTIONS_TABLE_NAME);
        onCreate(db);
    }



}
