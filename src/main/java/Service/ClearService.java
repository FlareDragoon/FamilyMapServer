package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Result.ClearResult;

public class ClearService extends BaseService {

    public ClearResult Clear() {
        db = new Database();
        try {
            db.OpenConnection();
            db.ClearTables();

            if (VerifyClear()) {
                db.CloseConnection(true);
                return new ClearResult(true, "Clear succeeded. Database is empty.");
            } else {
                db.CloseConnection(false);
                return new ClearResult(false,
                        "Error: One or more tables were not cleared in the database.");
            }
        } catch (DataAccessException e){
            try {
                db.CloseConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new ClearResult(false, "Error: Clear failed.");
        }
    }
}
