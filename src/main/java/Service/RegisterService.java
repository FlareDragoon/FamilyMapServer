package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Model.PersonModel;
import Model.UserModel;
import Request.RegisterRequest;
import Result.RegisterResult;

public class RegisterService extends BaseService {

    /**
     * Parses request item for needed values to send to other functions
     * @return the result form created in ReturnResult
     */
    public RegisterResult Register(RegisterRequest request) {
        db = new Database();
        try {
            db.OpenConnection();

            UserModel checkUser = FindUser(request.getUsername());
            if (checkUser == null) {
                if (!ValidateGender(request.getGender())) {
                    db.CloseConnection(false);
                    return new RegisterResult(null, null,
                            null, false, "Invalid gender.");
                }
                String personID = NewUUID();
                UserModel user = new UserModel(request.getUsername(), request.getPassword(),
                        request.getEmail(), request.getFirstName(), request.getLastName(),
                        request.getGender(), personID);
                new UserDAO(db.GetConnection()).InsertUser(user);

                PersonModel person = new PersonModel(personID, request.getUsername(),
                        request.getFirstName(), request.getLastName(), request.getGender(),
                        null, null, null);

                new GenerateFamilyTreeServiceHelper(request.getUsername(), db)
                        .GenerateFamilyTree(person, 4);

                String authToken = NewUUID();
                InsertAuthToken(request.getUsername(), authToken);

                db.CloseConnection(true);
                return new RegisterResult(authToken, request.getUsername(),
                        personID, true, null);

            } else {
                db.CloseConnection(false);
                return new RegisterResult(null, null,
                        null, false, "Error: Username already exists.");
            }

        } catch (DataAccessException e) {
            try {
                db.CloseConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new RegisterResult(null, null,
                    null, false, "Error: Registration failed.");
        }
    }

    private boolean ValidateGender(String gender) {
        boolean isMale = false;
        boolean isFemale = false;
        if (gender.equals("m")) {
            isMale = true;
        } else if (gender.equals("f")) {
            isFemale = true;
        }

        if (!isMale && !isFemale) {
            return false;
        } else {
            return true;
        }
    }
}
