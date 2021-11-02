package DataAccess;

import Model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends BaseDAO {
    /**
     * Initializes connection with database
     * @param conn
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds user to the table
     * @param user user model to add to the database
     */
    public void InsertUser(UserModel user) throws DataAccessException {
        String sql = "INSERT INTO user (username, password, email, firstName, " +
                "lastName, gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting user into the database");
        }
    }

    /**
     * Retrieves user from the database
     * @param username username to search for
     * @return user associated with the username
     */
    public UserModel FindUser(String username) throws DataAccessException {
        UserModel user;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE username = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new UserModel(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));
                return user;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while searching for user in the database");
        } finally {
            if(rs != null) {
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
     * Returns array of all users in the database
     * @return array of current users
     */
    public UserModel[] FindAllUsers() {
        ArrayList<UserModel> users = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM user;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();

            while (rs.next()) {
                UserModel user = new UserModel(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));
                users.add(user);
            }

            UserModel[] output = new UserModel[users.size()];
            output = users.toArray(output);

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
     * Deletes a single user from the database
     * @param username user to delete
     */
    public void DeleteUser(String username) {
        String sql = "DELETE FROM user WHERE username = ?;";
        super.DeleteItem(sql, username);
    }

    /**
     * Clears the user table
     */
    public void DeleteAllUsers() {
        String sql = "DELETE FROM user;";
        super.DeleteAll(sql);
    }
}
