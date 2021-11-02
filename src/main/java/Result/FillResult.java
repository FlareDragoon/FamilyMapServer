package Result;

public class FillResult extends BaseResult{
    /**
     * Result object based on following parameters
     * @param message String with success or error message
     * @param success boolean describing success or failure
     */
    public FillResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
