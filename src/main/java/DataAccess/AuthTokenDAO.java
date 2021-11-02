package DataAccess;

import Model.AuthTokenModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthTokenDAO extends BaseDAO{

    /**
     * Initializes connection with database
     * @param conn connection to database
     */
    public AuthTokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts authToken to authToken database
     * @param authToken authToken to be added
     */
    public void InsertAuthToken(AuthTokenModel authToken) throws DataAccessException {
        String sql = "INSERT INTO authToken(authToken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting token into the database");
        }
    }

    /**
     * Finds the authToken from the database
     * @param authTokenValue value of authToken to check in database
     */
    public AuthTokenModel FindAuthToken(String authTokenValue) throws DataAccessException{
        AuthTokenModel authToken;
        String sql = "SELECT * FROM authToken WHERE authToken = ?;";
        ResultSet rs = null;
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authTokenValue);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthTokenModel(rs.getString("authToken"),
                        rs.getString("username"));
                return authToken;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while finding token");
        } finally {
            if (rs!= null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Returns all auth tokens
     * @return all authtokens stored in database
     */
    public AuthTokenModel[] FindAllTokens() {
        ArrayList<AuthTokenModel> tokens = new ArrayList<>();

        ResultSet rs = null;
        String sql = "SELECT * FROM authToken;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();

            while (rs.next()) {
                AuthTokenModel token = new AuthTokenModel(rs.getString("authToken"),
                        rs.getString("username") );
                tokens.add(token);

            }

            AuthTokenModel[] output = new AuthTokenModel[tokens.size()];
            output = tokens.toArray(output);

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Clears single authToken from database upon logout
     * @param authToken authToken to remove
     */
    public void DeleteAuthToken(String authToken) {
        String sql = "DELETE FROM authToken WHERE authToken = ?;";
        super.DeleteItem(sql, authToken);
    }

    /**
     * Clears all authTokens from database
     */
    public void DeleteAllTokens() {
        String sql = "DELETE FROM authToken;";
        super.DeleteAll(sql);
    }

    /**
     * Removes all tokens for user
     * @param username user to remove tokens for
     */
    public void DeleteUsersAuthTokens(String username) {
        String sql = "DELETE FROM authToken WHERE username = ?;";
        super.DeleteItem(sql, username);
    }
}
