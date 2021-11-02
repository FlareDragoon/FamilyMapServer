package Request;

public class BaseRequest {
    /**
     * A unique username for the new user stored as a String
     */
    protected String username;
    /**
     * A password for the new user stored as a String
     */
    protected String password;

    protected String authtoken;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
