package login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import lehigh.cse.memcare.R;
import midtier.login.LoginService;
import ui.login.LoginPresenter;
import ui.login.LoginView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by andrewmcmullen on 9/25/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    private LoginView view;
    @Mock
    private LoginService service;
    private LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, service);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("");
        presenter.onLoginClicked();
        verify(view).showUsernameError(R.string.username_error);
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("Username");
        when(view.getPassword()).thenReturn("");
        presenter.onLoginClicked();
        verify(view).showPasswordError(R.string.password_error);
    }

    @Test
    public void shouldStartHomeActivityWhenUsernameAndPasswordAreCorrect() throws Exception{
        when(view.getUsername()).thenReturn("Username");
        when(view.getPassword()).thenReturn("Password");
        when(service.login("Username", "Password")).thenReturn(true);
        presenter.onLoginClicked();

        verify(view).startHomeActivity();
    }

    @Test
    public void shouldShowLoginErrorWhenUsernameAndPasswordAreInvalid() throws Exception {
        when(view.getUsername()).thenReturn("Username");
        when(view.getPassword()).thenReturn("Password");
        when(service.login("Username", "Password")).thenReturn(false);
        presenter.onLoginClicked();

        verify(view).showLoginError(R.string.login_failed);
    }

    @Test
    public void shouldClearDBOnClick() throws Exception {
        presenter.onClearDBClick();
        verify(view).showClearDbMessage();
    }

    @Test
    public void shouldStartRegistrationPageOnClick() throws Exception {
        presenter.onRegistrationClick();
        verify(view).startRegistrationActvity();
    }
}
