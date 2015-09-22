package medtier.DAO;

import android.database.Cursor;
import medtier.TO.CaregiverDTOImpl;
import common.CaregiverTableIndex;
import medtier.database.DatabaseHelperImpl;
import static common.CaregiverTableConstants.*;

public class CaregiverDAOImpl implements CaregiverDAO{


    public CaregiverDTOImpl retrieve_Caregiver(String username) {
        CaregiverDTOImpl caregiverDTO;
        String firstname;
        String lastname;
        String id;

        Cursor res = DatabaseHelperImpl.query("select * from " + TABLE_NAME + " where USER_NAME = '" + username + "'");
        res.moveToNext();
        firstname = res.getString(CaregiverTableIndex.INDEX_FIRST_NAME.toValue());
        lastname = res.getString(CaregiverTableIndex.INDEX_LAST_NAME.toValue());
        id = res.getString(CaregiverTableIndex.INDEX_ID.toValue());

        caregiverDTO = new CaregiverDTOImpl(firstname, lastname, username, id);
        return caregiverDTO;
    }
}
