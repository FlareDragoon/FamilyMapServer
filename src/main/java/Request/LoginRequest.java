package Request;

public class LoginRequest extends BaseRequest {

    /**
     * Creates a Request object with the following information
     * @param username the username to check in the database
     * @param password the matching password of legitimate users
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
