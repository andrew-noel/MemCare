package ui.registration.patient_registration;

import lehigh.cse.memcare.R;
import midtier.registration.RegistrationService;
import ui.registration.patient_registration.RegisterPatientView;

/**
 * Created by Noel on 10/14/15.
 */
public class RegisterPatientPresenter {

    public RegisterPatientView view;
    public RegistrationService service;

    public RegisterPatientPresenter(RegisterPatientView view, RegistrationService service) {
        this.service = service;
        this.view = view;
    }

    public void onRegisterClicked() {
        String firstname;
        String lastname;
        String age;
        String gender;

        boolean error = false;
//TODO: Actually fix these error messages. Didn't fully implement because sleep is a thing.
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
            if (service.insertData_registerPatient(firstname, lastname, age, gender) == false) {
                view.showRegistationError();
            } else {
                view.returnToTestActivity();
            }
        }

    }
}
