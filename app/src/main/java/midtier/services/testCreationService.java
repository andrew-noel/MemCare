package midtier.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import midtier.DatabaseHelper;

import static common.TestCreationConstants.*;
import static common.QuestionsConstants.*;
import static common.TestResultsConstants.*;

/**
 * Created by andrewmcmullen on 11/1/15.
 */
public class testCreationService extends DatabaseHelper{

    public testCreationService(Context context){
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TEST_CREATE_TABLE_NAME + " " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + TESTCREATE_COLUMN_2 + ", " +
                "" + TESTCREATE_COLUMN_3 + ", " +
                "" + TESTCREATE_COLUMN_4 + ", " +
                "" + TESTCREATE_COLUMN_5 + ", " +
                "" + TESTCREATE_COLUMN_6 + ")");

        db.execSQL("create table " + QUESTIONS_TABLE_NAME + " " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + QUESTION_COLUMN_2 + ", " +
                "" + QUESTION_COLUMN_3 + ", " +
                "" + QUESTION_COLUMN_4 + ", " +
                "" + QUESTION_COLUMN_5 + ")");

        db.execSQL("create table " + RESULTS_TABLE_NAME + " " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + RESULTS_COLUMN_1 + ", " +
                "" + RESULTS_COLUMN_2 + ", " +
                "" + RESULTS_COLUMN_3 + ", " +
                "" + RESULTS_COLUMN_4 + ", " +
                "" + RESULTS_COLUMN_5 + ", " +
                "" + RESULTS_COLUMN_6 + ")");


    }

    public boolean insertData_createTest(String owner, String patient_full_name, String testName, String testType, String dateOfCreation) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(TESTCREATE_COLUMN_2, owner);
        contentValues.put(TESTCREATE_COLUMN_3, patient_full_name);
        contentValues.put(TESTCREATE_COLUMN_4, testName);
        contentValues.put(TESTCREATE_COLUMN_5, testType);
        contentValues.put(TESTCREATE_COLUMN_6, dateOfCreation);
        long result = db.insert(TEST_CREATE_TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public boolean insertData_addQuestion(String testName, String URI, String person_name, String faceIndex){
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION_COLUMN_2, testName);
        contentValues.put(QUESTION_COLUMN_3, URI);
        contentValues.put(QUESTION_COLUMN_4, person_name);
        contentValues.put(QUESTION_COLUMN_5, faceIndex);
        long result = db.insert(QUESTIONS_TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public boolean insertData_Results(String patient_firstname, String patient_lastname, String username, String testname, String score, String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put(RESULTS_COLUMN_1, patient_firstname);
        Log.d("NOEL:", patient_firstname);
        contentValues.put(RESULTS_COLUMN_2, patient_lastname);
        contentValues.put(RESULTS_COLUMN_3, username);
        contentValues.put(RESULTS_COLUMN_4, testname);
        contentValues.put(RESULTS_COLUMN_5, date);
        contentValues.put(RESULTS_COLUMN_6, score);
        long result = db.insert(RESULTS_TABLE_NAME, null, contentValues);

        return result != -1;

    }


    public void dropTable(){
        this.delete_table(TEST_CREATE_TABLE_NAME);
        this.delete_table(QUESTIONS_TABLE_NAME);
        onCreate(db);
    }



}
