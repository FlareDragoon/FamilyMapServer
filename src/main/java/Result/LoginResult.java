package Result;

public class LoginResult extends BaseResult {

    /**
     * Result Object for the Login Request
     * @param authtoken A unique string identifier
     * @param username the username of the user
     * @param personID the personID for the attached user
     * @param success True if operation succeeded, false otherwise
     * @param message An error message for failed requests
     */
    public LoginResult(String authtoken, String username, String personID,
                boolean success, String message) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;
    }

}
