package DAOTest;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthTokenModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class AuthTokenDAOTest {
    private Database db;
    private AuthTokenDAO authTokenDAO;
    private AuthTokenModel token1;
    private AuthTokenModel token2;

    @BeforeEach
    public void SetUp() throws DataAccessException {

        db = new Database();

        Connection conn = db.GetConnection();
        db.ClearTables();
        authTokenDAO = new AuthTokenDAO(conn);

        token1 = new AuthTokenModel("123", "user1");
        token2 = new AuthTokenModel("234", "user2");

    }

    @AfterEach
    public void TearDown() throws DataAccessException{
        db.CloseConnection(false);
    }

    @Test
    public void FindTokenSuccess() throws DataAccessException {
        authTokenDAO.InsertAuthToken(token1);
        AuthTokenModel compareToken = authTokenDAO.FindAuthToken("123");
        Assertions.assertEquals(token1, compareToken);
    }

    @Test
    public void FindTokenFail() throws DataAccessException {
        Assertions.assertNull(authTokenDAO.FindAuthToken(null));
        Assertions.assertNull(authTokenDAO.FindAuthToken("345"));
    }

    @Test
    public void InsertTokenSuccess() throws DataAccessException {
        authTokenDAO.InsertAuthToken(token1);
        AuthTokenModel compareToken = authTokenDAO.FindAuthToken("123");

        Assertions.assertNotNull(authTokenDAO.FindAuthToken("123"));
        Assertions.assertEquals(token1, compareToken);

        authTokenDAO.InsertAuthToken(token2);
        compareToken = authTokenDAO.FindAuthToken("234");

        Assertions.assertNotNull(authTokenDAO.FindAuthToken("234"));
        Assertions.assertEquals(token2, compareToken);
    }

    @Test
    public void InsertTokenFail() throws DataAccessException {
        authTokenDAO.InsertAuthToken(token1);

        Assertions.assertThrows(DataAccessException.class, ()-> {authTokenDAO.InsertAuthToken(token1);});

        AuthTokenModel badToken = new AuthTokenModel(null, null);
        Assertions.assertThrows(DataAccessException.class, ()-> {authTokenDAO.InsertAuthToken(badToken);});
    }

    @Test
    public void DeleteAllTokensTest() throws DataAccessException {
        authTokenDAO.InsertAuthToken(token1);
        authTokenDAO.InsertAuthToken(token2);
        Assertions.assertNotNull(authTokenDAO.FindAuthToken("123"));
        Assertions.assertNotNull(authTokenDAO.FindAuthToken("234"));

        authTokenDAO.DeleteAllTokens();
        Assertions.assertNull(authTokenDAO.FindAuthToken("123"));
        Assertions.assertNull(authTokenDAO.FindAuthToken("234"));
    }

    @Test
    public void DeleteTokenTest() throws DataAccessException {
        authTokenDAO.InsertAuthToken(token1);
        authTokenDAO.InsertAuthToken(token2);

        authTokenDAO.DeleteAuthToken("123");
        Assertions.assertNull(authTokenDAO.FindAuthToken("123"));

        AuthTokenModel compareToken = authTokenDAO.FindAuthToken("234");
        Assertions.assertEquals(token2, compareToken);
    }

    @Test
    public void DeleteUsersTokensTest() throws DataAccessException {
        authTokenDAO.InsertAuthToken(token1);
        AuthTokenModel token3 = new AuthTokenModel("abc", "user1");
        authTokenDAO.InsertAuthToken(token3);

        Assertions.assertNotNull(authTokenDAO.FindAuthToken("123"));
        Assertions.assertNotNull(authTokenDAO.FindAuthToken("abc"));

        authTokenDAO.DeleteUsersAuthTokens("user1");
        Assertions.assertNull(authTokenDAO.FindAuthToken("123"));
        Assertions.assertNull(authTokenDAO.FindAuthToken("abc"));
    }
}
