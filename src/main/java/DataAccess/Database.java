package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection conn;

    public Connection OpenConnection() throws DataAccessException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:familyMapDatabase.sqlite";
            conn = DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    public Connection GetConnection() throws DataAccessException {
        if(conn == null) {
            return OpenConnection();
        } else {
            return conn;
        }
    }

    public void CloseConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                conn.commit();
            } else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void ClearTables() throws DataAccessException {
        new PersonDAO(conn).DeleteAllPeople();
        new EventDAO(conn).DeleteAllEvents();
        new UserDAO(conn).DeleteAllUsers();
        new AuthTokenDAO(conn).DeleteAllTokens();
    }
}