package Result;

import Model.EventModel;

public class GetAllFamilyEventsResult extends BaseResult {
    /**
     * Array of family events retrieved from database
     */
    private EventModel[] data;

    /**
     * Result object with all the family events retrieved from the database
     * @param data an array of events for all members of the family tree
     * @param success returns true if successful, false otherwise
     * @param message stores error messages
     */
    public GetAllFamilyEventsResult(EventModel[] data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public EventModel[] getData() {
        return data;
    }

    public void setData(EventModel[] data) {
        this.data = data;
    }

}
