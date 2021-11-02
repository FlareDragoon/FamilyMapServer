package Result;

public class BaseResult {
    protected boolean success;
    protected String message;

    /**
     * Stores a unique authToken for the user as a String
     */
    protected String authtoken;
    /**
     * Stores the username as a String
     */
    protected String username;
    /**
     * Stores the PersonID as a String
     */
    protected String personID;

    /**
     * The username received from the database
     */
    protected String associatedUsername;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }
}
