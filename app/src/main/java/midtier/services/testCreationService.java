package midtier.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import midtier.DatabaseHelper;

import static common.TestCreationConstants.*;

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

    public void dropTable(){
        this.delete_table(TEST_CREATE_TABLE_NAME);
        onCreate(db);
    }

}
