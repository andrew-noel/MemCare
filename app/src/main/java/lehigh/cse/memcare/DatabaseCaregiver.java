package lehigh.cse.memcare;

/**
 * Created by Noel on 9/18/15.
 */
public interface DatabaseCaregiver {

    public boolean insertData_registerCaregiver(String first_name, String last_name, String username, String password);

    public boolean check_if_username_exists(String username);

    public boolean checkPassword(String username, String password);

    public String get_first_Name(String username, String password);


}
