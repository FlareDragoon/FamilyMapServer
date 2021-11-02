package Request;

public class RegisterRequest extends BaseRequest {
    /**
     * An email for the new user stored as a String
     */
    private String email;
    /**
     * The first name of the user stored as a String
     */
    private String firstName;
    /**
     * The last name of the user stored as a String
     */
    private String lastName;
    /**
     * The gender of the user stored as a String
     */
    private String gender;

    /**
     * Creates a Register Request object based on given input
     * @param username a username to add to the database
     * @param password a password to add to the database
     * @param email an email to add to the database
     * @param firstName a first name to add to the database
     * @param lastName a last name to add to the database
     * @param gender the gender ('m' or 'f') to add to the database
     */
    public RegisterRequest(String username, String password, String email,
                           String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
