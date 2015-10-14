package ui.login;

import common.CaregiverTableConstants;
import lehigh.cse.memcare.R;
import midtier.login.LoginService;
import common.CaregiverTableConstants.*;

public class LoginPresenter {

    public LoginView view;
    public LoginService service;

    public LoginPresenter(LoginView view, LoginService service){
        this.view = view;
        this.service = service;
    }

    public void onLoginClicked(){
        String username = view.getUsername();
        if (username.isEmpty()){
            view.showUsernameError(R.string.username_error);
            return;
        }
        String password = view.getPassword();
        if (password.isEmpty()){
            view.showPasswordError(R.string.password_error);
            return;
        }
        boolean loginSucceeded = service.login(username, password);
        if (loginSucceeded){
            view.startHomeActivity();
            return;
        }
        view.showLoginError(R.string.login_failed);
    }

    public void onClearDBClick() {
        view.showClearDbMessage();
        service.delete_table(CaregiverTableConstants.CAREGIVER_TABLE_NAME);
        //service.clear_database();
    }

    public void onRegistrationClick() {
        view.startRegistrationActvity();
    }
}
