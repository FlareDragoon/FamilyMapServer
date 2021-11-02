package Model;

public class PersonModel {
    /**
     * The Person's unique ID is stored as a string
     */
    private String personID;
    /**
     * The person's username (if a user) stored as a string
     */
    private String associatedUsername;
    /**
     * The person's first name stored as a string
     */
    private String firstName;
    /**
     * The person's last name stored as a string
     */
    private String lastName;
    /**
     * The person's gender (either 'm' or 'f') stored as a string
     */
    private String gender;
    /**
     * The father's ID received as a string (possibly null)
     */
    private String fatherID;
    /**
     * The mother's ID stored as a string (possibly null)
     */
    private String motherID;
    /**
     * The spouse's ID stored as a string (possibly null)
     */
    private String spouseID;

    /**
     * Creates a Person based on the given input parameters:
     * @param personID receives the ID of the individual
     * @param username receives the associated username for this person (if applicable)
     * @param firstName receives the first name of the individual
     * @param lastName receives the last name of the individual
     * @param gender receives the gender (either 'm' or 'f') of the individual
     * @param fatherID receives the father's ID. Possibly null
     * @param motherID receives the mother's ID. Possibly null
     * @param spouseID receives the spouse's ID. Possibly null.
     */

    public PersonModel(String personID, String associatedUsername, String firstName, String lastName,
                       String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }


    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof PersonModel) {
            PersonModel oPerson = (PersonModel) o;
            return oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender().equals(getGender()) &&
                    (oPerson.getFatherID() == null && getFatherID() == null ||
                            oPerson.getFatherID().equals(getFatherID())) &&
                    (oPerson.getMotherID() == null && getMotherID() == null ||
                            oPerson.getMotherID().equals(getMotherID())) &&
                    (oPerson.getSpouseID() == null && getSpouseID() == null ||
                            oPerson.getSpouseID().equals(getSpouseID()));
        } else {
            return false;
        }
    }
}
