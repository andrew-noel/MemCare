package medtier.DAO;

import medtier.TO.CaregiverDTOImpl;

public interface CaregiverDAO {

    CaregiverDTOImpl retrieve_Caregiver(String username);

}
