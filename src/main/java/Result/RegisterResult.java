package Result;


public class RegisterResult extends BaseResult {
    /**
     * Creates a Result Object based on the following parameters:
     * @param authtoken If successful, receives the authToken string of the user
     * @param username If successful receives username of user
     * @param personID If successful, receives personID of user
     * @param message If failed, receives the error message
     * @param success Either true if registration succeeded or false if it failed
     */
    public RegisterResult(String authtoken, String username, String personID,
                          boolean success, String message) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;
    }

    /**
     * Prints successful operations to command line
     */
    public void PrintSuccessResult() {

    }

    /**
     * Prints failed operations to command line
     */
    public void PrintFailedResult() {

    }



}
