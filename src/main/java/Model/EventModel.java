package Model;

public class EventModel {
    /**
     * A unique ID for each event stored as a String
     */
    private String eventID;
    /**
     * The username of the user that this event is associated with stored as a String
     */
    private String associatedUsername;
    /**
     * The unique ID of the person this event is associated with stored as a String
     */
    private String personID;
    /**
     * The latitude where this event took place stored as a float
     */
    private Float latitude;
    /**
     * The Longitude where this event took place stored as a float
     */
    private Float longitude;
    /**
     * The country where this event took place stored as a String
     */
    private String country;
    /**
     * The city where this event took place stored as a String
     */
    private String city;
    /**
     * The type of event - e.g. baptism, birth, death, marriage - stored as a String
     */
    private String eventType;
    /**
     * The year this event took place stored as an int
     */
    private Integer year;

    /**
     * Creates an Event object with the given parameters as received:
     * @param eventID receives the unique event ID
     * @param associatedUsername receives the username of the person associated with this event
     * @param personID receives the person ID associated with this event
     * @param longitude receives the longitude of the event in question
     * @param latitude receives the latitude of the event in question
     * @param country receives the country where the event took place
     * @param city receives the city where the event took place
     * @param eventType receives what type of event this is, e.g baptism, birth, death, marriage
     * @param year receives the year this event took place in.
     */
    public EventModel(String eventID, String associatedUsername, String personID,
                      Float latitude, Float longitude, String country,
                      String city, String eventType, Integer year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }


    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String associatedPersonID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof EventModel) {
            EventModel oEvent = (EventModel) o;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude().equals(getLatitude()) &&
                    oEvent.getLongitude().equals(getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear().equals(getYear());
        } else {
            return false;
        }
    }
}
