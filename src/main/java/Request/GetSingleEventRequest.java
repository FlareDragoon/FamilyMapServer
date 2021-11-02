package Request;

public class GetSingleEventRequest extends BaseRequest {
    /**
     * Event ID to find
     */
    private String eventID;

    /**
     * Receives event ID from address of API
     * @param eventID event ID to find in database
     */
    public GetSingleEventRequest(String eventID, String authtoken) {
        this.eventID = eventID;
        this.authtoken = authtoken;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

}
