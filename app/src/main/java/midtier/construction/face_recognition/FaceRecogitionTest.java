package midtier.construction.face_recognition;

import android.net.Uri;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrewmcmullen on 10/13/15.
 */

//TODO: refactor Test to Quiz to avoid confusion?
public class FaceRecogitionTest {

    private String id;

    private int numPhotos;
    private String username;
    private String patient_name;
    private Date creation_date;

    private Map questions = new HashMap<Uri, String>();


    public FaceRecogitionTest(String username, String patient_name, Date creation_date){
        this.username = username;
        this.patient_name = patient_name;
        this.creation_date = creation_date;
        numPhotos = 0;
    }

    public int getNumPhotos(){
        return numPhotos;
    }

    public String getUsername(){
        return username;
    }

    public String getPatient_name(){
        return patient_name;
    }

    public Date getCreation_date(){
        return this.creation_date;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void addPicture(Uri uri, String name){
        questions.put(uri, name);
    }

}
