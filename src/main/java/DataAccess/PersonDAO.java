package DataAccess;

import Model.PersonModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDAO extends BaseDAO {
    /**
     * Initializes connection with database
     * @param conn connection to family map database
     */
    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a person model to the database
     * @param person person model to be added to the database
     */
    public void InsertPerson(PersonModel person) throws DataAccessException {
        String sql = "INSERT INTO person (personID, associatedUsername, firstName, lastName, " +
                "gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4,person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting person into the database");
        }
    }

    /**
     * Returns a person model based on the ID input
     * @param personID ID to search through person table
     * @return person model for matching ID
     */
    public PersonModel FindPerson(String personID) throws DataAccessException {
        PersonModel person;
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new PersonModel(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while searching for person in the database");
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
     * Gathers an array of all people within the family tree
     * @param personID personID to parse family tree and add ancestors to array
     * @return array of person models for the given ID
     */
    public PersonModel[] FindFamily(String personID) throws DataAccessException{
        ArrayList<PersonModel> family = new ArrayList<>();

        try {
            FindFamilyHelper(personID, family);

            PersonModel[] output = new PersonModel[family.size()];
            output = family.toArray(output);

            return output;

        } catch (Exception e) {
            throw new DataAccessException("Problem finding family");
        }
    }

    public void FindFamilyHelper(String personID, ArrayList<PersonModel> family) throws DataAccessException {
        try {
            //adds person to family array
            family.add(FindPerson(personID));
            //if it is the first person, add spouse to family array
            if (family.size() == 1) {
                String spouseID = FindPerson(personID).getSpouseID();
                if (spouseID != null && FindPerson(spouseID) != null) {
                    family.add(FindPerson(spouseID));
                }
            }

            //recursively add fathers and mothers to the family array
            String nextID = FindPerson(personID).getFatherID();
            if (nextID != null && FindPerson(nextID) != null) {
                FindFamilyHelper(nextID, family);
            }

            nextID = FindPerson(personID).getMotherID();
            if (nextID != null && FindPerson(nextID) != null) {
                FindFamilyHelper(nextID, family);
            }

        } catch (Exception e) {
            throw new DataAccessException("Problem finding family");
        }
    }

    /**
     * Returns all person model objects from database
     * @return array of person model objects
     */
    public PersonModel[] FindAllPeople() {
        ArrayList<PersonModel> people = new ArrayList<>();

        ResultSet rs = null;
        String sql = "SELECT * FROM person;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();

            while (rs.next()) {
                PersonModel person = new PersonModel(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                people.add(person);
            }

            PersonModel[] output = new PersonModel[people.size()];
            output = people.toArray(output);

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
     * Removes the person from the database
     * @param personID ID of person to be removed
     */
    public void DeletePerson(String personID) throws DataAccessException {
        String sql = "DELETE FROM person WHERE personID = ?;";
        super.DeleteItem(sql, personID);
    }

    /**
     * Clears the person table of all entries
     */
    public void DeleteAllPeople() {
        String sql = "DELETE FROM person;";
        super.DeleteAll(sql);
    }

}
