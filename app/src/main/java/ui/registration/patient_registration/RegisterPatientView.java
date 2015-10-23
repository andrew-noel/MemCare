package ui.registration.patient_registration;

/**
 * Created by Noel on 10/14/15.
 */
public interface RegisterPatientView {
    String getFirstName();

    String getLastName();

    String getAge();

    String getGender();

    void showFirstnameError(int registration_firstname_error);

    void showLastnameError(int registration_lastname_error);

    void showAgeError(int registration_age_error);

    void showGenderError(int registration_gender_error);

    void showPatientAlreadyExistsError(int registration_patientAlreadyExists_error);

    void showRegistationError();

    void returnToTestActivity();

    void showClearPatientDBMessage();
}
