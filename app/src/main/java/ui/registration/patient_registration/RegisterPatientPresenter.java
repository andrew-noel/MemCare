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
        /*if ((gender = view.getGender()).isEmpty()) {
            //view.showGenderError(5);
            error = true;
        }*/
        if ((lastname = view.getLastName()).isEmpty()) {
            view.showLastnameError(5);
            error = true;
        }
        if ((firstname = view.getFirstName()).isEmpty()) {
            view.showFirstnameError(5);
            error = true;
        }
        if ((age = view.getAge()).isEmpty()) {
            view.showAgeError(5);
            error = true;
        }

        if (service.patient_exists(firstname)) {
            view.showPatientAlreadyExistsError(5);
            error = true;
        }

        if (error == false) {
            if (service.insertData_registerPatient(firstname, lastname, age, "Male") == false) {
                view.showRegistationError();
            } else {
                view.returnToTestActivity();
            }
        }

    }
}
