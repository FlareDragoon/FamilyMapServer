package Result;

public class GetSinglePersonResult extends BaseResult {
    /**
     * The first name received from the database
     */
    private String firstName;
    /**
     * The last name received from the database
     */
    private String lastName;
    /**
     * The gender ('m' or 'f') received from the database
     */
    private String gender;
    /**
     * The father's ID (possibly null) received from the database
     */
    private String fatherID;
    /**
     * The mother's ID (possibly null) received from the database
     */
    private String motherID;
    /**
     * The Spouse's ID (possibly null) received from the database
     */
    private String spouseID;


    /**
     * Result form based on the request to get an individual person with following data:
     * @param associatedUsername the user to get info for
     * @param personID the ID of the person
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     * @param gender the gender of the person ('m' or 'f')
     * @param fatherID the ID of the person's father (possibly null)
     * @param motherID the ID of the person's mother (possibly null)
     * @param spouseID the ID of the person's spouse (possibly null)
     * @param success true if successful, false if failed
     * @param message an error message for failed requests
     */
    public GetSinglePersonResult(String associatedUsername, String personID, String firstName,
                                 String lastName, String gender, String fatherID, String motherID,
                                 String spouseID, boolean success, String message) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this. fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
        this.message = message;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

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
}
