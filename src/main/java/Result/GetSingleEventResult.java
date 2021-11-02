package Result;

public class GetSingleEventResult extends BaseResult {
    /**
     * Unique event ID retrieved from database
     */
    private String eventID;
    /**
     * Latitude value retrieved from database
     */
    private Float latitude;
    /**
     * Longitude value retrieved from database
     */
    private Float longitude;
    /**
     * Country of event retrieved from database
     */
    private String country;
    /**
     * City of event retrieved from database
     */
    private String city;
    /**
     * Type of event retrieved from database (e.g. birth, death, baptism)
     */
    private String eventType;
    /**
     * Year of event retrieved from database
     */
    private Integer year;

    /**
     * Result form with the following data from database:
     * @param associatedUsername user associated with this event
     * @param eventID unique event ID for event
     * @param personID person ID associated with event
     * @param latitude latitude of event
     * @param longitude longitude of event
     * @param country country where event occurred
     * @param city city where event occurred
     * @param year year when event occurred
     * @param eventType type of event (e.g. birth, death, baptism)
     * @param success true for successful operations, false otherwise
     * @param message error message for failed operations
     */
    public GetSingleEventResult(String associatedUsername, String eventID, String personID,
                                Float latitude, Float longitude, String country, String city,
                                String eventType, Integer year, boolean success, String message) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
        this.message = message;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
