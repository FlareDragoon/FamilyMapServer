package ServiceTest;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import Service.ClearService;
import Service.LoadService;
import Service.LoginService;
import Service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class LoginTest {
    private Database db;
    private RegisterRequest register;
    private LoginRequest login;

    @BeforeEach
    public void SetUp() throws DataAccessException {

        db = new Database();
        db.OpenConnection();
        register = new RegisterRequest("username", "password", "none@none.com",
                "Johann", "Schmidt", "m");
        login = new LoginRequest("username", "password");
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.Register(register);
    }

    @AfterEach
    public void TearDown() throws DataAccessException{
        new ClearService().Clear();
        db.CloseConnection(false);
    }

    @Test
    public void LoginSuccess() {
        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.Login(login);

        Assertions.assertNotNull(loginResult.getAuthtoken());
    }

    @Test
    public void LoginFail() {
        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.Login(login);

        Assertions.assertNotNull(loginResult.getAuthtoken());

        LoginRequest badLogin = new LoginRequest("bad", "password");
        LoginResult badResult = loginService.Login(badLogin);

        Assertions.assertNull(badResult.getAuthtoken());
    }


}
