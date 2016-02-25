package midtier.models;

import midtier.DAOs.CaregiverDAO;
import midtier.TO.CaregiverTO;

/**
 * Created by andrewmcmullen on 2/24/16.
 */
public class userModel {

    private static volatile userModel instance;

    private static CaregiverDAO dao;
    private CaregiverTO  to;

    private userModel() { }

    /* Singleton model. Only one user can be logged in at a time */
    public static userModel getInstance() {
        if (instance == null) {
            synchronized (userModel.class) {
                dao = new CaregiverDAO();
                if (instance == null)
                    instance = new userModel();
            }
        }
        return instance;
    }

    public void updateModel(String username) {
        to = dao.retrieve_Caregiver(username);
    }

    //TODO: extend for other user attributes
    public String getUsername() {
        return to.get_username();
    }

}
