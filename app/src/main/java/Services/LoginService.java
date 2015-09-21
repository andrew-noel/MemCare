package Services;

import android.content.Context;

import common.CaregiverTableIndex;
import database.DatabaseHelperImpl;

import static common.CaregiverTableConstants.*;

public class LoginService extends DatabaseHelperImpl {

    public LoginService(Context context) {
        super(context);
    }
    public static boolean username_exists(String username){
        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        return DatabaseHelperImpl.res.getCount() != 0;
    }

    public static boolean checkPassword(String username, String password){
        if (!username_exists(username)){
            return false;
        }
        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        DatabaseHelperImpl.res.moveToNext();
        String db_password = DatabaseHelperImpl.res.getString(CaregiverTableIndex.INDEX_PASSWORD.toValue());
        //Log.d("PASSWORD", "PASSWORD = " + db_password);
        return password.equals(db_password);
    }
}
