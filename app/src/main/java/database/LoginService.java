package database;

import android.content.Context;

import common.CaregiverTableIndex;

import static common.CaregiverTableConstants.*;

/**
 * Created by andrewmcmullen on 9/19/15.
 */
public class LoginService extends DatabaseHelperImpl{

    public LoginService(Context context) {
        super(context);
    }
    public static boolean username_exists(String username){
        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        return res.getCount() != 0;
    }

    public static boolean checkPassword(String username, String password){
        if (!username_exists(username)){
            return false;
        }
        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        res.moveToNext();
        String db_password = res.getString(CaregiverTableIndex.INDEX_PASSWORD.toValue());
        //Log.d("PASSWORD", "PASSWORD = " + db_password);
        return password.equals(db_password);
    }
}
