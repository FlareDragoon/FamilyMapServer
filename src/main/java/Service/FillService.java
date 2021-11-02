package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import Model.PersonModel;
import Request.FillRequest;
import Result.FillResult;

public class FillService extends BaseService {
    /**
     * Parses request and runs methods
     *
     * @return result form created in ReturnResult
     */
    public FillResult FillTree(FillRequest request) {
        db = new Database();
        try {
            db.OpenConnection();

            String personID = GetUsersID(request.getUsername());
            PersonModel person = new PersonDAO(db.GetConnection()).FindPerson(personID);

            ClearOldData(request);

            new GenerateFamilyTreeServiceHelper(person.getAssociatedUsername(), db).GenerateFamilyTree(person,
                    request.getGenerations());

            db.CloseConnection(true);

            int peopleAdded = (int) Math.pow(2, request.getGenerations() + 1) - 1;
            int minEventsAdded = peopleAdded * 2;

            String returnMessage = "Successfully added " + (peopleAdded) + " persons and " +
                    (minEventsAdded) + " events";
            return new FillResult(true, returnMessage);
        } catch (DataAccessException e) {
            try {
                db.CloseConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new FillResult(false, "Error: Could not generate new data");
        }
    }

    private void ClearOldData(FillRequest request) throws DataAccessException {
        new EventDAO(db.GetConnection()).DeleteUsersEvents(request.getUsername());

        String personID = GetUsersID(request.getUsername());
        PersonModel user = new PersonDAO(db.GetConnection()).FindPerson(personID);
        PersonModel[] family = new PersonDAO(db.GetConnection()).FindFamily(personID);

        for (PersonModel person : family) {
            new PersonDAO(db.GetConnection()).DeletePerson(person.getPersonID());
        }

        user.setFatherID(null);
        user.setMotherID(null);
        user.setSpouseID(null);
    }
}