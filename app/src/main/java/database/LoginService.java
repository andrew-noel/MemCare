package database;

import android.content.Context;

/**
 * Created by andrewmcmullen on 9/19/15.
 */
public class LoginService extends DatabaseHelperImpl{

    public LoginService(Context context) {
        super(context);
    }
    public static boolean check_if_username_exists(String username){
        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        return res.getCount() != 0;
    }

    public static boolean checkPassword(String username, String password){
        if (!check_if_username_exists(username)){
            return false;
        }
        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        res.moveToNext();
        String db_password = res.getString(INDEX_PASSWORD);
        //Log.d("PASSWORD", "PASSWORD = " + db_password);
        return password.equals(db_password);
    }
}
