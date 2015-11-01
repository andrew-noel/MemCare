package midtier.DAOs;

import midtier.TO.CaregiverTO;

/**
 * Created by andrewmcmullen on 9/21/15.
 */
public interface CaregiverDAOInt {

    CaregiverTO retrieve_Caregiver(String username);
}
