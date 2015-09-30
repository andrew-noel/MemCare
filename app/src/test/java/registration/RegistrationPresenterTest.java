package registration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import lehigh.cse.memcare.R;
import midtier.registration.RegistrationService;
import ui.registration.RegistrationPresenter;
import ui.registration.RegistrationView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by andrewmcmullen on 9/29/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class RegistrationPresenterTest {
    @Mock
    private RegistrationView view;
    @Mock
    private RegistrationService service;
    private RegistrationPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new RegistrationPresenter(view, service);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("");
        when(view.getFirstName()).thenReturn("firstname");
        when(view.getLastName()).thenReturn("lastname");
        when(view.getPassword()).thenReturn("password");
        when(service.username_exists("")).thenReturn(true);
        presenter.onLoginClicked();
        verify(view).showUsernameError(R.string.Registration_username_error);
    }

    @Test
    public void shouldShowErrorMessageWhenFirstNameIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("username");
        when(view.getFirstName()).thenReturn("");
        when(view.getLastName()).thenReturn("lastname");
        when(view.getPassword()).thenReturn("password");
        when(service.username_exists("username")).thenReturn(true);
        presenter.onLoginClicked();
        verify(view).showFirstNameError(R.string.Registration_firstname_error);
    }

    @Test
    public void shouldShowErrorMessageWhenLastNameIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("username");
        when(view.getFirstName()).thenReturn("firstname");
        when(view.getLastName()).thenReturn("");
        when(view.getPassword()).thenReturn("password");
        when(service.username_exists("username")).thenReturn(true);
        presenter.onLoginClicked();
        verify(view).showLastNameError(R.string.Registration_lastname_error);
    }
    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("username");
        when(view.getFirstName()).thenReturn("firstname");
        when(view.getLastName()).thenReturn("lastname");
        when(view.getPassword()).thenReturn("");
        when(service.username_exists("username")).thenReturn(true);
        presenter.onLoginClicked();
        verify(view).showPasswordError(R.string.Registration_password_error);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameAlreadyExists() throws Exception{
        when(view.getUsername()).thenReturn("username");
        when(view.getFirstName()).thenReturn("firstname");
        when(view.getLastName()).thenReturn("lastname");
        when(view.getPassword()).thenReturn("password");
        when(service.username_exists("username")).thenReturn(true);
        presenter.onLoginClicked();
        verify(view).showUsernameAlreadyExistsError(R.string.Registration_userAlreadyExists_error);
    }


    @Test
    public void shouldReturnToLoginOnSuccessfulRegister() throws Exception{
        when(view.getUsername()).thenReturn("username");
        when(view.getFirstName()).thenReturn("firstname");
        when(view.getLastName()).thenReturn("lastname");
        when(view.getPassword()).thenReturn("password");
        when(service.username_exists("username")).thenReturn(false);
        when(service.insertData_registerCaregiver("firstname", "lastname", "username", "password")).thenReturn(true);
        presenter.onLoginClicked();
        verify(view).returnToLoginActivity();
    }



}
