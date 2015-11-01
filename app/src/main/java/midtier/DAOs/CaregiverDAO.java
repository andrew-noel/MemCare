package midtier.DAOs;

import android.database.Cursor;

import common.CaregiverTableIndex;
import midtier.TO.CaregiverTO;
import midtier.TO.CaregiverTOImpl;
import midtier.DatabaseHelper;

import static common.CaregiverTableConstants.*;

public class CaregiverDAO implements CaregiverDAOInt {


    public CaregiverTO retrieve_Caregiver(String username) {
        CaregiverTO caregiverDAO;
        String firstname;
        String lastname;
        String id;

        Cursor res = DatabaseHelper.query("select * from " + CAREGIVER_TABLE_NAME + " where USER_NAME = '" + username + "'");
        res.moveToNext();
        firstname = res.getString(CaregiverTableIndex.INDEX_FIRST_NAME.toValue());
        lastname = res.getString(CaregiverTableIndex.INDEX_LAST_NAME.toValue());
        id = res.getString(CaregiverTableIndex.INDEX_ID.toValue());

        caregiverDAO = new CaregiverTOImpl(firstname, lastname, username, id);
        return caregiverDAO;
    }
}
