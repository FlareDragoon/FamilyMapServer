package DataAccess;

import Model.EventModel;
import Model.PersonModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventDAO extends BaseDAO{
    /**
     * Initializes connection with database
     * @param conn
     */
    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts event to the database
     * @param event event model to be inserted to the database
     */
    public void InsertEvent(EventModel event) throws DataAccessException{

        String sql = "INSERT INTO event (eventID, associatedUsername, personID, latitude, " +
                "longitude, country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting event into the database");
        }
    }

    /**
     * Finds and returns one event from database
     * @param eventID eventID to find in the database
     * @return Event Model object containing the info found from the ID
     */
    public EventModel FindOneEvent(String eventID) throws DataAccessException{
        EventModel event;
        ResultSet rs = null;
        String sql = "SELECT * FROM event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new EventModel(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"),
                        rs.getFloat("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while finding event");
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
     * FInds and returns all events for the family
     * @param family family array from Person DAO that contains family information
     * @return Array of all events from the person's family tree
     */
    public EventModel[] FindAllFamilyEvents(PersonModel[] family) throws DataAccessException {
        ArrayList<EventModel> events = new ArrayList<>();
        for (PersonModel person : family) {
            ResultSet rs = null;
            String sql = "SELECT * FROM event WHERE personID = ?;";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, person.getPersonID());
                rs = stmt.executeQuery();

                MakeEventsList(rs, events);
            } catch (SQLException e) {
                throw new DataAccessException("Error encountered while finding event");
            } finally {
                if(rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (events.size() > 0) {
            EventModel[] output = new EventModel[events.size()];
            output = events.toArray(output);

            return output;
        } else {
            return null;
        }

    }

    /**
     * Returns all events in the event table
     * @return array of event model objects
     */
    public EventModel[] FindAllEvents() {
        ArrayList<EventModel> events = new ArrayList<>();

        ResultSet rs = null;
        String sql = "SELECT * FROM event;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();

            MakeEventsList(rs, events);

            EventModel[] output = new EventModel[events.size()];
            output = events.toArray(output);

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

    private void MakeEventsList(ResultSet rs, ArrayList<EventModel> events) throws SQLException {
        while (rs.next()) {
            EventModel event = new EventModel(rs.getString("eventID"),
                    rs.getString("associatedUsername"), rs.getString("personID"),
                    rs.getFloat("latitude"), rs.getFloat("longitude"),
                    rs.getString("country"), rs.getString("city"),
                    rs.getString("eventType"), rs.getInt("year"));
            events.add(event);
        }
    }

    /**
     * Removes one event
     * @param eventID ID of event to be removed
     */
    public void DeleteEvent(String eventID) {
        String sql = "DELETE FROM event WHERE eventID = ?;";
        super.DeleteItem(sql, eventID);
    }

    /**
     * Clears Event table
     */
    public void DeleteAllEvents() {
        String sql = "DELETE FROM event;";
        super.DeleteAll(sql);
    }

    /**
     * Removes all events associated with a user
     * @param associatedUsername user to clear events for
     */
    public void DeleteUsersEvents(String associatedUsername) {
        String sql = "DELETE FROM event WHERE associatedUsername = ?;";
        super.DeleteItem(sql, associatedUsername);
    }
}
