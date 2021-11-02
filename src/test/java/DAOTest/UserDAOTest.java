package DAOTest;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.UserModel;
import org.junit.jupiter.api.*;

import java.sql.Connection;

public class UserDAOTest {
    private Database db;
    private UserDAO userDAO;
    private UserModel user1;
    private UserModel user2;

    @BeforeEach
    public void SetUp() throws DataAccessException {

        db = new Database();

        Connection conn = db.GetConnection();
        db.ClearTables();
        userDAO = new UserDAO(conn);
        user1 = new UserModel("user1", "password", "none@none.com", "John",
            "Gutenberg", "m", "abc");
        user2 = new UserModel("user2", "P@$$w0rD", "nope@none.com", "Mary",
            "Ann", "f", "bcd");
    }

    @AfterEach
    public void TearDown() throws DataAccessException{
        db.CloseConnection(false);
    }

    @Test
    public void FindUserSuccess() throws DataAccessException{
        userDAO.InsertUser(user1);
        UserModel compareUser = userDAO.FindUser("user1");
        Assertions.assertEquals(user1, compareUser);
    }

    @Test
    public void FindUserFail() throws DataAccessException {
        Assertions.assertNull(userDAO.FindUser(null));
        Assertions.assertNull(userDAO.FindUser("asdf"));
    }

    @Test
    public void InsertUserSuccess() throws DataAccessException {
        userDAO.InsertUser(user1);
        UserModel compareUser = userDAO.FindUser("user1");
        Assertions.assertNotNull(userDAO.FindUser("user1"));
        Assertions.assertEquals(user1, compareUser);

        userDAO.InsertUser(user2);
        compareUser = userDAO.FindUser("user2");

        Assertions.assertNotNull(userDAO.FindUser("user2"));
        Assertions.assertEquals(user2, compareUser);
    }

    @Test
    public void InsertUserFail() throws DataAccessException{
        userDAO.InsertUser(user1);
        Assertions.assertThrows(DataAccessException.class, () -> {
            userDAO.InsertUser(user1); });

        UserModel badUser = new UserModel (null, null, null, null,
            null, null, null);
        Assertions.assertThrows(DataAccessException.class, () -> {
            userDAO.InsertUser(badUser); });
    }

    @Test
    public void FindAllUsers() throws DataAccessException {
        userDAO.InsertUser(user1);
        UserModel [] users = userDAO.FindAllUsers();
        Assertions.assertEquals(1, users.length);

        userDAO.InsertUser(user2);
        users = userDAO.FindAllUsers();
        Assertions.assertEquals(2, users.length);
    }

    @Test
    public void DeleteAllUsersTest() throws DataAccessException{
        userDAO.InsertUser(user1);
        userDAO.InsertUser(user2);
        Assertions.assertNotNull(userDAO.FindUser("user1"));
        Assertions.assertEquals(user1.getPersonID(), userDAO.FindUser("user1").getPersonID());

        userDAO.DeleteAllUsers();
        Assertions.assertNull(userDAO.FindUser("user1"));
        Assertions.assertNull(userDAO.FindUser("user2"));
    }

    @Test
    public void DeleteUserTest() throws DataAccessException {
        userDAO.InsertUser(user1);
        userDAO.InsertUser(user2);
        Assertions.assertNotNull(userDAO.FindUser("user1"));

        UserModel compareUser = userDAO.FindUser("user1");
        Assertions.assertEquals(user1, compareUser);

        userDAO.DeleteUser("user1");

        Assertions.assertNull((userDAO.FindUser("user1")));

        compareUser = userDAO.FindUser("user2");
        Assertions.assertEquals(user2, compareUser);
    }
}