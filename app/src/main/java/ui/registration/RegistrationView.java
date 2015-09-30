package ui.registration;

/**
 * Created by andrewmcmullen on 9/29/15.
 */
public interface RegistrationView {
    String getUsername();

    String getLastName();

    String getFirstName();

    String getPassword();

    void showUsernameError(int username_error);

    void showFirstNameError(int registration_firstname_error);

    void showLastNameError(int registration_lastname_error);

    void showPasswordError(int registration_password_error);

    void showUsernameAlreadyExistsError(int registration_userAlreadyExists_error);

    void returnToLogin();

}
