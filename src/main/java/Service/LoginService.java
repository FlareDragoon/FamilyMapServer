package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.UserModel;
import Request.LoginRequest;
import Result.LoginResult;

public class LoginService extends BaseService {
    /**
     * Parses data from request and calls other functions
     * @return the login result form created in ReturnResult
     */
    public LoginResult Login(LoginRequest request) {
        db = new Database();
        try {
            boolean loginSuccessful = false;
            db.OpenConnection();

            UserModel user = FindUser(request.getUsername());

            String username;
            String personID;

            if (user != null) {
                if (user.getUsername().equals(request.getUsername()) &&
                    user.getPassword().equals(request.getPassword())) {

                    loginSuccessful = true;

                    username = user.getUsername();
                    personID = user.getPersonID();

                    String authToken = NewUUID();
                    InsertAuthToken(username, authToken);

                    db.CloseConnection(true);

                    return new LoginResult(authToken, username,
                            personID, true, null);
                }
            }
            if (!loginSuccessful){
                db.CloseConnection(false);

                return new LoginResult(null, null,
                        null, false, "Error: User not found.");
            }

        } catch (DataAccessException e) {
            try {
                db.CloseConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new LoginResult(null, null,
                    null, false, "Error: Login failed.");
        }
        return null;
    }


}
