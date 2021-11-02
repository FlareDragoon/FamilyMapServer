package Service;

import DataAccess.*;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Request.LoadRequest;
import Result.LoadResult;

public class LoadService extends BaseService {
    /**
     * Parses request form and calls methods
     * @return result form created in ReturnResult
     */
    public LoadResult Load(LoadRequest request) {
        try {
            db = new Database();
            db.OpenConnection();

            if(!CheckRequestData(request)) {
                return new LoadResult(false, "Error: Request contained null data.");
            }

            db.ClearTables();
            if (VerifyClear()) {
                for (UserModel user : request.getUsers()) {
                    new UserDAO(db.GetConnection()).InsertUser(user);
                }
                for (PersonModel person : request.getPersons()) {
                    new PersonDAO(db.GetConnection()).InsertPerson(person);
                }
                for (EventModel event : request.getEvents()) {
                    new EventDAO(db.GetConnection()).InsertEvent(event);
                }

                if (ValidateLoad(request)) {
                    int usersAdded = new UserDAO(db.GetConnection()).FindAllUsers().length;
                    int peopleAdded = new PersonDAO(db.GetConnection()).FindAllPeople().length;
                    int eventsAdded = new EventDAO(db.GetConnection()).FindAllEvents().length;

                    String returnMessage = "Successfully added " + (usersAdded) + " users, " +
                            (peopleAdded) + " persons, and " + (eventsAdded) + " events.";

                    db.CloseConnection(true);
                    return new LoadResult(true, returnMessage);
                } else {
                    db.CloseConnection(false);
                    return new LoadResult(false, "Error: One or more tables failed to load.");
                }
            } else {
                db.CloseConnection(false);
                return new LoadResult(false, "Error: Failed to clear database");
            }
        } catch (DataAccessException e) {
            try {
                db.CloseConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new LoadResult(false, "Error: Failed to load database");
        }
    }

    private boolean CheckRequestData(LoadRequest request) {
        if (request.getUsers() == null) {
            return false;
        }
        if (request.getPersons() == null) {
            return false;
        }
        if (request.getEvents() == null) {
            return false;
        }

        return true;

    }

    public boolean ValidateLoad(LoadRequest request) {
        boolean output = false;
        try {
            for (UserModel user : request.getUsers()) {
                if (new UserDAO(db.GetConnection()).FindUser(user.getUsername()) == null) {
                    return output;
                }
            }
            for (PersonModel person : request.getPersons()) {
                if (new PersonDAO(db.GetConnection()).FindPerson(person.getPersonID()) == null) {
                    return output;
                }
            }
            for (EventModel event : request.getEvents()) {
                if (new EventDAO(db.GetConnection()).FindOneEvent(event.getEventID()) == null) {
                    return output;
                }
            }

            output = true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return output;

    }
}
