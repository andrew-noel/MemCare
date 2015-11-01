package ui.registration.user_registration;

import lehigh.cse.memcare.R;
import midtier.services.RegistrationService;

/**
 * Created by andrewmcmullen on 9/29/15.
 */
public class RegistrationPresenter {
    public RegistrationView view;
    public RegistrationService service;

    public RegistrationPresenter(RegistrationView view, RegistrationService service) {
        this.service = service;
        this.view = view;
    }

    public void onLoginClicked() {
        String firstname;
        String lastname;
        String username;
        String password;

        boolean error = false;

        if ((username = view.getUsername()).isEmpty()) {
            view.showUsernameError(R.string.Registration_username_error);
            error = true;
        }
        if ((lastname = view.getLastName()).isEmpty()) {
            view.showLastNameError(R.string.Registration_lastname_error);
            error = true;
        }
        if ((firstname = view.getFirstName()).isEmpty()) {
            view.showFirstNameError(R.string.Registration_firstname_error);
            error = true;
        }
        if ((password = view.getPassword()).isEmpty()) {
            view.showPasswordError(R.string.Registration_password_error);
            error = true;
        }

        if (service.username_exists(username)){
            view.showUsernameAlreadyExistsError(R.string.Registration_userAlreadyExists_error);
            error = true;
        }

        if (error == false){
            if (service.insertData_registerCaregiver(firstname, lastname, username, password) == false){
                view.showRegistrationError();
            }else {
                view.returnToLoginActivity();
            }
        }


    }
}
