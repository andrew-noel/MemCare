package midtier.TO;

/**
 * Created by Noel on 9/20/15.
 */
public class CaregiverTOImpl implements CaregiverTO {

    private String firstname;
    private String lastname;
    private String username;
    private String id;

    public CaregiverTOImpl(String firstname, String lastname, String username, String id){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.id = id;
    }
    @Override
    public String get_firstname() {
        return  firstname;
    }

    @Override
    public String get_lastname() {
        return lastname;
    }

    @Override
    public String get_username() {
        return username;
    }

    @Override
    public String get_id(){
        return id;
    }
}
