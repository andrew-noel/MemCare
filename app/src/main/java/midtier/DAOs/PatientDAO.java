package midtier.DAOs;

import java.util.List;

import midtier.TO.PatientTO;

/**
 * Created by Noel on 10/16/15.
 */
public interface PatientDAO {

    PatientTO getPatientData(String patientFirstName, String patientLastName);

    //TODO: should be user specific, not every
    List<String> getListOfPatientNames();
}
