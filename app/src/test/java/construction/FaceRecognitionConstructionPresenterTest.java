package construction;

/**
 * Created by andrewmcmullen on 10/13/15.
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ui.construction.face_recognition_construction.FaceRecognitionConstructionPresenter;
import ui.construction.face_recognition_construction.FaceRecognitionConstructionView;
import ui.registration.user_registration.RegistrationPresenter;

@RunWith(MockitoJUnitRunner.class)
public class FaceRecognitionConstructionPresenterTest {

    @Mock
    private FaceRecognitionConstructionView view;
    private FaceRecognitionConstructionPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new FaceRecognitionConstructionPresenter(view);
    }
}
