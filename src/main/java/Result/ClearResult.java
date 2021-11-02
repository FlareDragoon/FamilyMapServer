package Result;

public class ClearResult extends BaseResult {

    /**
     * Deletes all data in database
     * @param message Tells if operation was successful or if an error was encountered
     * @param success Boolean operator to tell if operation was successful
     */
    public ClearResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
