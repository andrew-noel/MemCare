package midtier.registration;

import midtier.TO.CaregiverTO;

/**
 * Created by andrewmcmullen on 9/21/15.
 */
public interface CaregiverDAO {

    CaregiverTO retrieve_Caregiver(String username);
}
