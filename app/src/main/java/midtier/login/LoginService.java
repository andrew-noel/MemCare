package midtier.login;

import android.content.Context;

import common.CaregiverTableIndex;
import midtier.DatabaseHelperImpl;

import static common.CaregiverTableConstants.*;

/**
 * Created by andrewmcmullen on 9/19/15.
 */
public class LoginService extends DatabaseHelperImpl {

    public LoginService(Context context) {
        super(context);
    }
    public boolean login(String username, String password){
        res = db.rawQuery("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'", null);
        if (DatabaseHelperImpl.res.getCount() != 0){
            return false;
        }
        DatabaseHelperImpl.res.moveToNext();
        String db_password = DatabaseHelperImpl.res.getString(CaregiverTableIndex.INDEX_PASSWORD.toValue());
        //Log.d("PASSWORD", "PASSWORD = " + db_password);
        return password.equals(db_password);
    }
}
