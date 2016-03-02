package midtier;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static common.table_constants.*;


public class DatabaseHelper extends SQLiteOpenHelper implements DatabaseHelperInt {

    public static SQLiteDatabase db;
    public static Cursor res;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
        onCreate(db);
    }

/*
    @Override
    public abstract void onCreate(SQLiteDatabase db);
  */
    @Override
    public void onCreate(SQLiteDatabase db) {

        /* create caregiver table */
        db.execSQL("create table IF NOT EXISTS " + CAREGIVER_TABLE_NAME + " (" + CG_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                            "" + CG_COLUMN_FIRST_NAME + " TEXT, " +
                                                                            "" + CG_COLUMN_LAST_NAME + " TEXT, " +
                                                                            "" + CG_COLUMN_USERNAME + " TEXT, " +
                                                                            "" + CG_COLUMN_PASSWORD + " TEXT )");

        /* Create patient's table */
        db.execSQL("create table IF NOT EXISTS " + PATIENT_TABLE_NAME   + " (" + P_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                            "" + P_COLUMN_FIRSTNAME + " TEXT, " +
                                                                            "" + P_COLUMN_LASSTNAME + " TEXT, " +
                                                                            "" + P_COLUMN_AGE + " TEXT, " +
                                                                            "" + P_COLUMN_GENDER + " TEXT, " +
                                                                            "" + P_COLUMN_CAREGIVER + " TEXT )");

        /* create test table */
        db.execSQL("create table IF NOT EXISTS " + TEST_CREATE_TABLE_NAME   + " (" + TT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                                "" + TT_COLUMN_CAREGIVER + " TEXT, " +
                                                                                "" + TT_COLUMN_PATIENT + " TEXT, " +
                                                                                "" + TT_COLUMN_TESTNAME + " TEXT, " +
                                                                                "" + TT_COLUMN_TEST_TYPE + " TEXT, " +
                                                                                "" + TT_COLUMN_DATE + " TEXT )");

        /* create results table */
        db.execSQL("create table IF NOT EXISTS " + RESULTS_TABLE_NAME   + " (" + R_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                            "" + R_COLUMN_PATIENT_FIRSTNAME + " TEXT, " +
                                                                            "" + R_COLUMN_PATIENT_LASTNAME + " TEXT, " +
                                                                            "" + R_COLUMN_CAREGIVER + " TEXT, " +
                                                                            "" + R_COLUMN_TESTNAME + " TEXT, " +
                                                                            "" + R_COLUMN_DATE + " TEXT, " +
                                                                            "" + R_COLUMN_SCORE + " TEXT )");

        /* create questions table */
        db.execSQL("create table IF NOT EXISTS " + QUESTIONS_TABLE_NAME   + " (" + Q_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                              "" + Q_COLUMN_PERSON_NAME + " TEXT, " +
                                                                              "" + Q_COLUMN_FACE_INDEX + " TEXT, " +
                                                                              "" + Q_COLUMN_IMAGE_URI + " TEXT, " +
                                                                              "" + R_COLUMN_TESTNAME + " TEXT )");
    }


    public void create(String tableName){

        if(tableName == CAREGIVER_TABLE_NAME) {
            db.execSQL("create table " + CAREGIVER_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, USER_NAME TEXT, PASSWORD TEXT )");
        }
        else if(tableName == PATIENT_TABLE_NAME) {
            db.execSQL("create table " + PATIENT_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, AGE TEXT, GENDER TEXT, CAREGIVER TEXT )");
        }
        else if(tableName == RESULTS_TABLE_NAME){
            db.execSQL("create table " + RESULTS_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, PATIENT_FIRSTNAME TEXT, PATIENT_LASTNAME TEXT, CAREGIVER_USERNAME TEXT, TEST_NAME TEXT, DATE TEXT, SCORE TEXT )");
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CAREGIVER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE_NAME);
        onCreate(db);
    }


    // TODO: delete eventually
    @Override
    public void delete_table(String tableName){
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        create(tableName);
    }


    public static Cursor query(String queryString){
        res = db.rawQuery(queryString, null);
        return res;
    }



}

