package lehigh.cse.memcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

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

    public DatabaseHelper(Context context) {
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

    public boolean insertData_registerCaregiver(String first_name, String last_name, String username, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2, first_name);
        contentValues.put(COLUMN_3, last_name);
        contentValues.put(COLUMN_4, username);
        contentValues.put(COLUMN_5, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean check_if_user_exists(String username){
        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        return res.getCount() != 0;
    }

    public boolean checkPassword(String username, String password){
        if (!check_if_user_exists(username)){
            return false;
        }

        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);

        res.moveToNext();

        String db_password = res.getString(INDEX_PASSWORD);

        //Log.d("PASSWORD", "PASSWORD = " + db_password);
        return password.equals(db_password);


    }

    public String get_first_Name(String username, String password){

        if (!checkPassword(username, password)){
            return null;
        }

        String first_name;
        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        first_name = res.getString(INDEX_FIRST_NAME);
        Log.d("PASSWORD", "FIRST_NAME = " + first_name);
        return first_name;

    }

    public void clear_database(){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);


    }



}

