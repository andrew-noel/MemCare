package registration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import lehigh.cse.memcare.R;
import midtier.registration.RegistrationService;
import ui.registration.patient_registration.RegisterPatientPresenter;
import ui.registration.patient_registration.RegisterPatientView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Noel on 10/14/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class RegisterPatientPresenterTest {

    @Mock
    private RegisterPatientView view;
    @Mock
    private RegistrationService service;
    private RegisterPatientPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new RegisterPatientPresenter(view, service);
    }

    @Test
    public void shouldShowErrorMessageWhenFirstNameIsEmpty() throws Exception {
        when(view.getFirstName()).thenReturn("");
        when(view.getLastName()).thenReturn("Last name");
        when(view.getAge()).thenReturn("100");
        when(view.getGender()).thenReturn("Male");
        presenter.onRegisterClicked();
        verify(view).showFirstnameError(5);
    }

    @Test
    public void shouldShowErrorMessageWhenLastNameIsEmpty() throws Exception {
        when(view.getFirstName()).thenReturn("First name");
        when(view.getLastName()).thenReturn("");
        when(view.getAge()).thenReturn("100");
        when(view.getGender()).thenReturn("Male");
        presenter.onRegisterClicked();
        verify(view).showLastnameError(5);
    }

    @Test
    public void shouldShowErrorMessageWhenAgeIsEmpty() throws Exception {
        when(view.getFirstName()).thenReturn("First name");
        when(view.getLastName()).thenReturn("Last name");
        when(view.getAge()).thenReturn("");
        when(view.getGender()).thenReturn("Male");
        presenter.onRegisterClicked();
        verify(view).showAgeError(5);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameAlreadyExists() throws Exception{
        when(view.getFirstName()).thenReturn("firstname");
        when(view.getLastName()).thenReturn("lastname");
        when(view.getAge()).thenReturn("50");
        when(service.patient_exists("firstname")).thenReturn(true);
        presenter.onRegisterClicked();
        verify(view).showPatientAlreadyExistsError(5);
    }

    @Test
    public void shouldReturnToLoginOnSuccessfulRegister() throws Exception{
        when(view.getFirstName()).thenReturn("firstname");
        when(view.getLastName()).thenReturn("lastname");
        when(view.getAge()).thenReturn("33");
        when(service.patient_exists("firstname")).thenReturn(false);
        when(service.insertData_registerPatient("firstname", "lastname", "33", "Male")).thenReturn(true);
        presenter.onRegisterClicked();
        verify(view).returnToTestActivity();
    }

}
