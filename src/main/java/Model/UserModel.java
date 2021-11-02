package Model;

import passoffmodels.User;

public class UserModel {
    /**
     * The unique username of the user stored as a String
     */
    private String username;
    /**
     * The password of the user stored as a string
     */
    private String password;
    /**
     * The email of the user stored as a string
     */
    private String email;
    /**
     * The first name of the user stored as a string
     */
    private String firstName;
    /**
     * The last name of the user stored as a string
     */
    private String lastName;
    /**
     * The gender (either 'm' or 'f') stored as a String
     */
    private String gender;
    /**
     * The unique Person ID stored as a String
     */
    private String personID;

    /**
     * Creates a USer based on the given input received:
     * @param username The unique username of the user
     * @param password The password of the user
     * @param email The email of the user
     * @param firstName The first name of the User
     * @param lastName The last name of the user
     * @param gender The gender of the user (either 'm' or 'f')
     * @param personID The unique person ID of the user
     */
    public UserModel(String username, String password, String email, String firstName,
                     String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }


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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof UserModel) {
            UserModel oUser = (UserModel) o;
            return oUser.getUsername().equals(getUsername()) &&
                    oUser.getPersonID().equals(getPersonID()) &&
                    oUser.getEmail().equals(getEmail()) &&
                    oUser.getFirstName().equals(getFirstName()) &&
                    oUser.getLastName().equals(getLastName()) &&
                    oUser.getGender().equals(getGender()) &&
                    oUser.getPersonID().equals(getPersonID());
        } else {
            return false;
        }
    }
}
