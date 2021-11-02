package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.PersonModel;
import Request.GetFamilyRequest;
import Result.GetFamilyResult;

public class GetFamilyService extends BaseService {

    /**
     * Calls other methods in class
     * @param request contains user's authToken to identify user
     * @return result form created in ReturnResult
     */
    public GetFamilyResult FindFamily(GetFamilyRequest request) {
        db = new Database();
        try {
            db.OpenConnection();
            String username = AuthorizeKey(request.getAuthtoken());
            if (username != null) {
                String personID = GetUsersID(username);
                PersonModel[] family = GetFamilyTree(personID);

                db.CloseConnection(true);
                return new GetFamilyResult(family, true, null);
            } else {
                db.CloseConnection(false);
                return new GetFamilyResult(null, false, "Error: Username not found.");
            }
        } catch (Exception e) {
            try {
                db.CloseConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new GetFamilyResult(null, false, "Error: Could not find family.");
        }
    }
}
