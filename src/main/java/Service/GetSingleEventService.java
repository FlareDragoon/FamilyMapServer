package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.EventModel;
import Request.GetSingleEventRequest;
import Result.GetSingleEventResult;
import Result.LoginResult;

import java.io.IOException;

public class GetSingleEventService extends BaseService{
        /**
     * parses request and calls methods
     * @return Result created in ReturnResult
     */
    public GetSingleEventResult GetEvent(GetSingleEventRequest request) {
        db = new Database();
        try {
            db.OpenConnection();

            String tokensUsername = AuthorizeKey(request.getAuthtoken());

            if (tokensUsername != null) {
                EventModel event = new EventDAO(db.GetConnection()).FindOneEvent(request.getEventID());
                if (event != null && event.getAssociatedUsername().equals(tokensUsername)) {
                    db.CloseConnection(true);
                    return new GetSingleEventResult(event.getAssociatedUsername(), event.getEventID(),
                            event.getPersonID(), event.getLatitude(), event.getLongitude(),
                            event.getCountry(), event.getCity(), event.getEventType(),
                            event.getYear(), true, null);
                } else {
                    db.CloseConnection(false);

                    return new GetSingleEventResult(null, null, null,
                            null, null, null, null, null,
                            null, false, "Error: Event not found.");
                }
            } else {
                db.CloseConnection(false);

                return new GetSingleEventResult(null, null, null,
                        null, null, null, null, null,
                        null, false, "Error: AuthToken not found.");
            }

        } catch (DataAccessException e) {
            try {
                db.CloseConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new GetSingleEventResult(null, null, null,
                    null, null, null, null, null,
                    null, false, "Error: Could not get event from database.");
        }
    }
}
