package midtier.registration;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;

import midtier.DatabaseHelperImpl;
import midtier.TO.PatientTO;
import static common.PatientTableConstants.*;

/**
 * Created by Noel on 10/16/15.
 */
public class PatientDAOImpl implements PatientDAO {


//TODO: Finish the PatientDAOImpl because it's not quite done yet. Will work more in the morning.



    @Override
    public PatientTO getPatientData(String patientFirstName, String patientLastName) {
        return null;
    }

    @Override
    public List<String> getEveryPatientName() {
        List<String> names = new ArrayList<String>();

        Cursor res = DatabaseHelperImpl.query("select PATIENT_COLUMN_2, PATIENT_COLUMN_3 from " + PATIENT_TABLE_NAME + " ");



        return null;
    }
}
