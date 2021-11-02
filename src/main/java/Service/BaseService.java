package Service;

import DataAccess.*;
import Model.AuthTokenModel;
import Model.PersonModel;
import Model.UserModel;

import java.util.UUID;

public abstract class BaseService {
    protected Database db;

    public void InsertAuthToken(String username, String token) {
        try {
            AuthTokenModel authToken = new AuthTokenModel(token, username);
            new AuthTokenDAO(db.GetConnection()).InsertAuthToken(authToken);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public String NewUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public UserModel FindUser(String username) {
        try {
            return new UserDAO(db.GetConnection()).FindUser(username);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String AuthorizeKey(String token) {
        try {
            AuthTokenModel authorizedToken = new AuthTokenDAO(db.GetConnection()).FindAuthToken(token);
            if (authorizedToken != null) {
                return authorizedToken.getUsername();
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String GetUsersID(String username) {
        try {
            UserModel user = new UserDAO(db.GetConnection()).FindUser(username);
            if (user != null) {
                return user.getPersonID();
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PersonModel[] GetFamilyTree(String personID) {
        try {
            return new PersonDAO(db.GetConnection()).FindFamily(personID);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean VerifyClear() {
        try {
            if (new UserDAO(db.GetConnection()).FindAllUsers().length == 0 &&
                new PersonDAO(db.GetConnection()).FindAllPeople().length == 0 &&
                new AuthTokenDAO(db.GetConnection()).FindAllTokens().length == 0 &&
                new EventDAO(db.GetConnection()).FindAllEvents().length == 0) {

                return true;
            } else {
                return false;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }



}
