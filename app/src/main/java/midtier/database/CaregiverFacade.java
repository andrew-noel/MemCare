package midtier.database;

import android.database.Cursor;

import common.CaregiverDAO;
import common.CaregiverDAOImpl;
import common.CaregiverTableIndex;

import static common.CaregiverTableConstants.*;

/**
 * Created by Noel on 9/20/15.
 */
public class CaregiverFacade {


    public static CaregiverDAO retrieve_Caregiver(String username) {
        CaregiverDAO caregiverDAO;
        String firstname;
        String lastname;
        String id;

        Cursor res = DatabaseHelperImpl.query("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'");
        res.moveToNext();
        firstname = res.getString(CaregiverTableIndex.INDEX_FIRST_NAME.toValue());
        lastname = res.getString(CaregiverTableIndex.INDEX_LAST_NAME.toValue());
        id = res.getString(CaregiverTableIndex.INDEX_ID.toValue());

        caregiverDAO = new CaregiverDAOImpl(firstname, lastname, username, id);
        return caregiverDAO;
    }
}
