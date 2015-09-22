package medtier.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Noel on 9/18/15.
 */
public interface DatabaseHelper {

    void onCreate(SQLiteDatabase db);

    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    void clear_database();

}
