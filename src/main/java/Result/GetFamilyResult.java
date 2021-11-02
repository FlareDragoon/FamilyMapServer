package Result;

import Model.PersonModel;

public class GetFamilyResult extends BaseResult {
    /**
     * An array of person model objects received from the database
     */
    private PersonModel[] data;

    /**
     * Returns following data representing user's family tree:
     * @param data An array of person model objects representing each family member
     * @param success true for successful operations, false otherwise
     * @param message an error message stored as a string
     */
    public GetFamilyResult(PersonModel[] data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public PersonModel[] getData() {
        return data;
    }

    public void setData(PersonModel[] data) {
        this.data = data;
    }

}
