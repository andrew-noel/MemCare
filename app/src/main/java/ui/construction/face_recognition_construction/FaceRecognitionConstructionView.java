package ui.construction.face_recognition_construction;

import android.net.Uri;

import java.util.Map;

/**
 * Created by andrewmcmullen on 10/13/15.
 */
public interface FaceRecognitionConstructionView {

    void AuthenticateDropBox();

    String getPhotoName();

    void setPhoto(Uri image_path);

    Map<String, Uri> getPhotoURIMap();
}
