package Model;

public class AuthTokenModel {
    /**
     * A unique string to send to the server when a request is made
     */
    private String authToken;
    /**
     * The username associated with this particular authToken stored as a String
     */
    private String username;

    /**
     * Creates an authtoken model based on the following parameters
     * @param authToken a unique string to be sent to the server
     * @param username the username associated with this token
     */
    public AuthTokenModel(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof AuthTokenModel) {
            AuthTokenModel oToken = (AuthTokenModel) o;
            return oToken.getAuthToken().equals(getAuthToken()) &&
                    oToken.getUsername().equals(getUsername());
        } else {
            return false;
        }
    }
}
