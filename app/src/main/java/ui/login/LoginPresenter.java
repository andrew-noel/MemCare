package ui.login;

import lehigh.cse.memcare.R;
import midtier.login.LoginService;

public class LoginPresenter {

    private LoginView view;
    private LoginService service;

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
        service.clear_database();
    }

    public void onRegistrationClick() {
        view.startRegistrationActvity();
    }
}
