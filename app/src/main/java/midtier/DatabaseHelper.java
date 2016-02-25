package midtier;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static common.CaregiverTableConstants.*;
import static common.PatientTableConstants.*;

public class DatabaseHelper extends SQLiteOpenHelper implements DatabaseHelperInt {

    public static SQLiteDatabase db;
    public static Cursor res;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

/*
    @Override
    public abstract void onCreate(SQLiteDatabase db);
  */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO: create table on first run
        db.execSQL("create table " + CAREGIVER_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, USER_NAME TEXT, PASSWORD TEXT )");
        db.execSQL("create table " + PATIENT_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, AGE TEXT, GENDER TEXT, CAREGIVER TEXT )");
    }

    public void create(String tableName){

        if(tableName == CAREGIVER_TABLE_NAME) {
            db.execSQL("create table " + CAREGIVER_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, USER_NAME TEXT, PASSWORD TEXT )");
        }
        else if(tableName == PATIENT_TABLE_NAME) {
            db.execSQL("create table " + PATIENT_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, AGE TEXT, GENDER TEXT, CAREGIVER TEXT )");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CAREGIVER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE_NAME);
        onCreate(db);
    }

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

