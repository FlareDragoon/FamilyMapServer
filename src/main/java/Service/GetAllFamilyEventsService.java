package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.EventModel;
import Model.PersonModel;
import Request.GetAllFamilyEventsRequest;
import Result.GetAllFamilyEventsResult;

public class GetAllFamilyEventsService extends  BaseService{

    /**
     * Calls other methods in class
     * @param request contains user's authToken to identify user
     * @return result form created in ReturnResult
     */
    public GetAllFamilyEventsResult FindEvents(GetAllFamilyEventsRequest request) {
        db = new Database();
        try {
            db.OpenConnection();
            String username = AuthorizeKey(request.getAuthtoken());
            if (username != null) {
                String personID = GetUsersID(username);
                PersonModel[] family = GetFamilyTree(personID);

                EventModel[] familyEvents = new EventDAO(db.GetConnection()).FindAllFamilyEvents(family);

                db.CloseConnection(true);
                return new GetAllFamilyEventsResult(familyEvents, true, null);
            } else {
                db.CloseConnection(false);
                return new GetAllFamilyEventsResult(null, false,
                        "Error: Username not found.");
            }
        } catch (DataAccessException e) {
            try {
                db.CloseConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new GetAllFamilyEventsResult(null, false,
                    "Error: Could not find events.");
        }
    }
}
