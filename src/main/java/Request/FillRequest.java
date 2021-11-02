package Request;

public class FillRequest extends BaseRequest {
    /**
     * How many generations will be used (must be positive int, default is 4)
     */
    private int generations;

    /**
     * Fill Request with parameters taken from address
     * @param username user to fill info for
     * @param generations how many generations (default is 4)
     */
    public FillRequest(String username, int generations) {
        this.username = username;
        if (generations <= 0) {
            this.generations = 4;
        } else {
            this.generations = generations;
        }
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
