package Request;


public class GetSinglePersonRequest extends BaseRequest {
    /**
     * ID of person to find
     */
    private String personID;

    /**
     * Creates request from API address
     * @param personID ID of person to find
     */
    public GetSinglePersonRequest(String personID, String authtoken) {
        this.personID = personID;
        this.authtoken = authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
