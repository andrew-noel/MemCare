package midtier.DAOs;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;

import midtier.DatabaseHelper;
import midtier.TO.PatientTO;
import static common.PatientTableConstants.*;

/**
 * Created by Noel on 10/16/15.
 */
public class PatientDAO implements PatientDAOInt {

    @Override
    public PatientTO getPatientData(String patientFirstName, String patientLastName) {
        return null;
    }

    @Override
    public List<String> getListOfPatientNames() {
        //TODO: should be user specific
        List<String> names = new ArrayList<String>();
        String wholeName;

        Cursor res = DatabaseHelper.query("select * from " + PATIENT_TABLE_NAME);
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
