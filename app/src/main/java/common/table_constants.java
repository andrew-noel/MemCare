package common;

/**
 * Created by andrewmcmullen on 2/29/16.
 */
public class table_constants {

    public static final String DATABASE_NAME = "MemCare.db";

    /* Caregive table constants */
    public static final String CAREGIVER_TABLE_NAME = "Caregivers_table";

    public static final String CG_COLUMN_ID         = "ID";
    public static final String CG_COLUMN_TEST_NAME  = "TEST_NAME";
    public static final String CG_COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String CG_COLUMN_LAST_NAME  = "LAST_NAME";
    public static final String CG_COLUMN_USERNAME   = "USER_NAME";
    public static final String CG_COLUMN_PASSWORD   = "PASSWORD";

    /* Patient table constants  */
    public static final String PATIENT_TABLE_NAME    = "Patients_table";

    public static final String P_COLUMN_ID           = "ID";
    public static final String P_COLUMN_FIRSTNAME    = "FIRST_NAME";
    public static final String P_COLUMN_LASSTNAME    = "LAST_NAME";
    public static final String P_COLUMN_AGE          = "AGE";
    public static final String P_COLUMN_GENDER       = "GENDER";
    public static final String P_COLUMN_CAREGIVER    = "CAREGIVER";

    /* Test table constants */
    public static final String TEST_CREATE_TABLE_NAME = "TEST_TABLE";

    public static final String TT_COLUMN_ID           = "ID";
    public static final String TT_COLUMN_CAREGIVER    = "OWNER_USERNAME";
    public static final String TT_COLUMN_PATIENT      = "PATIENT_FULLNAME";
    public static final String TT_COLUMN_TESTNAME     = "TEST_NAME";
    public static final String TT_COLUMN_TEST_TYPE    = "TEST_TYPE";
    public static final String TT_COLUMN_DATE         = "DATE_OF_CREATION";

    /* Results table constants */
    public static final String RESULTS_TABLE_NAME = "Results_table";

    public static final String R_COLUMN_ID                = "ID";
    public static final String R_COLUMN_PATIENT_FIRSTNAME = "PATIENT_FIRSTNAME";
    public static final String R_COLUMN_PATIENT_LASTNAME  = "PATIENT_LASTNAME";
    public static final String R_COLUMN_CAREGIVER         = "CAREGIVER_USERNAME";
    public static final String R_COLUMN_TESTNAME          = "TEST_NAME";
    public static final String R_COLUMN_DATE              = "DATE";
    public static final String R_COLUMN_SCORE             = "SCORE";


    /* Questions table constants */
    public static final String QUESTIONS_TABLE_NAME = "QUESTIONS_TABLE";

    public static final String Q_COLUMN_ID           = "ID";
    public static final String Q_COLUMN_TESTNAME     = "TEST_NAME";
    public static final String Q_COLUMN_IMAGE_URI    = "IMAGE_URI";
    public static final String Q_COLUMN_PERSON_NAME  = "PERSON_NAME";
    public static final String Q_COLUMN_FACE_INDEX   = "FACDE_INDEX";


}
