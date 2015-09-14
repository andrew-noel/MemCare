package lehigh.cse.memcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andrewmcmullen on 9/13/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MemCare.db";
    public static final String TABLE_NAME = "Caregivers_table";

    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "FIRST_NAME";
    public static final String COLUMN_3 = "LAST_NAME";
    public static final String COLUMN_4 = "USER_NAME";
    public static final String COLUMN_5 = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, USERNAME TEXT, PASSWORD TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData_registerCaregiver(String first_name, String last_name, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2, first_name);
        contentValues.put(COLUMN_3, last_name);
        contentValues.put(COLUMN_4, username);
        contentValues.put(COLUMN_5, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }
        return true;
    }
}
