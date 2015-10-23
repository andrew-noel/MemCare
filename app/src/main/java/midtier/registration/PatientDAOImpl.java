package midtier.registration;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;

import common.PatientTableConstants;
import midtier.DatabaseHelperImpl;
import midtier.TO.PatientTO;
import static common.PatientTableConstants.*;

/**
 * Created by Noel on 10/16/15.
 */
public class PatientDAOImpl implements PatientDAO {






    @Override
    public PatientTO getPatientData(String patientFirstName, String patientLastName) {
        return null;
    }

    @Override
    public List<String> getEveryPatientName() {
        List<String> names = new ArrayList<String>();
        String wholeName;

        Cursor res = DatabaseHelperImpl.query("select * from " + PATIENT_TABLE_NAME);
        if(res.moveToFirst()){
            do{
                wholeName = res.getString(1) + " " + res.getString(2);

                names.add(wholeName);
            }while(res.moveToNext());
        }


        res.close();

        return names;
    }
}
