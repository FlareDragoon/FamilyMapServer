package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseDAO {
    protected Connection conn;

    public void DeleteAll(String sql) {
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteItem(String sql, String item) {
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
