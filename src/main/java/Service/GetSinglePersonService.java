package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.AuthTokenModel;
import Model.EventModel;
import Model.PersonModel;
import Request.GetSinglePersonRequest;
import Result.GetSingleEventResult;
import Result.GetSinglePersonResult;
import passoffmodels.Person;

public class GetSinglePersonService extends BaseService {
    /**
     * parses request and calls methods
     *
     * @return Result created in ReturnResult
     */
    public GetSinglePersonResult GetPerson(GetSinglePersonRequest request) {
        db = new Database();
        try {
            db.OpenConnection();
            String tokensUsername = AuthorizeKey(request.getAuthtoken());
            if (tokensUsername != null) {

                PersonModel person = new PersonDAO(db.GetConnection()).FindPerson(request.getPersonID());
                if (person.getAssociatedUsername().equals(tokensUsername)) {
                    if (person != null) {
                        db.CloseConnection(true);
                        return new GetSinglePersonResult(person.getAssociatedUsername(),
                                person.getPersonID(), person.getFirstName(), person.getLastName(),
                                person.getGender(), person.getFatherID(), person.getMotherID(),
                                person.getSpouseID(), true, null);
                    } else {
                        db.CloseConnection(false);

                        return new GetSinglePersonResult(null, null, null,
                                null, null, null, null, null,
                                false, "Error: Person not found.");
                    }
                } else {
                    db.CloseConnection(false);

                    return new GetSinglePersonResult(null, null, null,
                            null, null, null, null, null,
                            false, "Error: Incorrect authToken provided.");
                }
            } else {
                db.CloseConnection(false);

                return new GetSinglePersonResult(null, null, null,
                        null, null, null, null, null,
                        false, "Error: No authToken provided.");
            }

        } catch (DataAccessException e) {
            try {
                db.CloseConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new GetSinglePersonResult(null, null, null,
                    null, null, null, null, null,
                    false, "Error: Could not get person from database.");

        }
    }
}