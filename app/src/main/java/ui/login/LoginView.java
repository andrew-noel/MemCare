package ui.login;

/**
 * Created by andrewmcmullen on 9/25/15.
 */
public interface LoginView {

    String getUsername();

    void showUsernameError(int resId);

    String getPassword();

    void showPasswordError(int resId);

    void startHomeActivity();

    void showLoginError(int resId);
}
