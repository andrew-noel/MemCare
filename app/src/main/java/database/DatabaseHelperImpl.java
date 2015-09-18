package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperImpl extends SQLiteOpenHelper implements DatabaseHelper {

    public static final String DATABASE_NAME = "MemCare.db";
    public static final String TABLE_NAME = "Caregivers_table";

    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "FIRST_NAME";
    public static final String COLUMN_3 = "LAST_NAME";
    public static final String COLUMN_4 = "USER_NAME";
    public static final String COLUMN_5 = "PASSWORD";

    public static final int INDEX_ID = 0;
    public static final int INDEX_FIRST_NAME = 1;
    public static final int INDEX_LAST_NAME = 2;
    public static final int INDEX_USER_NAME = 3;
    public static final int INDEX_PASSWORD = 4;

    SQLiteDatabase db;
    Cursor res;

    public DatabaseHelperImpl(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, USER_NAME TEXT, PASSWORD TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void clear_database(){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }



}

