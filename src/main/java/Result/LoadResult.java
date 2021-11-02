package Result;

import Handler.BaseHandler;

public class LoadResult extends BaseResult {
    /**
     * Creates a result form based on load operation
     * @param message a string detailing any errors or success message
     * @param success true for successful operations, false for failed
     */
    public LoadResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
