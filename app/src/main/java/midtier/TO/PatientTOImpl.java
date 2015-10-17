package midtier.TO;

/**
 * Created by Noel on 10/16/15.
 */
public class PatientTOImpl implements PatientTO{

    private String patientFirstName;
    private String patientLastName;
    private String patientAge;
    private String patientGender;
    private String patientID;


    public PatientTOImpl(String patientID,String patientFirstName, String patientLastName, String patientAge, String patientGender){
        this.patientID = patientID;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
    }

    @Override
    public String getPatientFirstName() {
        return patientFirstName;
    }

    @Override
    public String getPatientLastName() {
        return patientLastName;
    }

    @Override
    public String getPatientAge() {
        return patientAge;
    }

    @Override
    public String getPatientGender() {
        return patientGender;
    }

    @Override
    public String getPatientID() {
        return patientID;
    }
}
