package midtier;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Noel on 9/18/15.
 */
public interface DatabaseHelperInt {

    void onCreate(SQLiteDatabase db);

    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    void delete_table(String table_name);



}
