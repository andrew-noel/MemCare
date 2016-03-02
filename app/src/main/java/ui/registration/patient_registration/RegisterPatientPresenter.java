package ui.registration.patient_registration;

import common.table_constants;
import lehigh.cse.memcare.R;
import midtier.services.RegistrationService;
import midtier.models.userModel;

/**
 * Created by Noel on 10/14/15.
 */
public class RegisterPatientPresenter {

    public RegisterPatientView view;
    public RegistrationService service;
    public userModel userModel;

    public RegisterPatientPresenter(RegisterPatientView view, RegistrationService service) {
        userModel = userModel.getInstance();
        this.service = service;
        this.view = view;
    }

    public void onRegisterClicked() {
        String firstname;
        String lastname;
        String age;
        String gender;


        boolean error = false;
        if ((gender = view.getGender()).isEmpty()) {
            view.showGenderError(R.string.Registration_gender_error);
            error = true;
        }
        if ((lastname = view.getLastName()).isEmpty()) {
            view.showLastnameError(R.string.Registration_lastname_error);
            error = true;
        }
        if ((firstname = view.getFirstName()).isEmpty()) {
            view.showFirstnameError(R.string.Registration_firstname_error);
            error = true;
        }
        if ((age = view.getAge()).isEmpty()) {
            view.showAgeError(R.string.Registration_age_error);
            error = true;
        }

        if (service.patient_exists(view.getFirstName(), view.getLastName())) {
            view.showPatientAlreadyExistsError(R.string.Registration_patientAlreadyExists_error);
            error = true;
        }

        if (error == false) {
            if (service.insertData_registerPatient(firstname, lastname, age, "Male", userModel.getUsername()) == false) {
                view.showRegistationError();
            } else {
                view.returnToTestActivity();
            }
        }

    }

    public void onClearDBClicked() {
        view.showClearPatientDBMessage();
        service.delete_table(table_constants.PATIENT_TABLE_NAME);
    }
}
