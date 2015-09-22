package midtier;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Noel on 9/18/15.
 */
public interface DatabaseHelper {

    public void onCreate(SQLiteDatabase db);

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    public void clear_database();

}
