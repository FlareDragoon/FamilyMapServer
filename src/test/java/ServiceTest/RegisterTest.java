package ServiceTest;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.ClearService;
import Service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterTest {
    private Database db;
    private RegisterRequest register;

    @BeforeEach
    public void SetUp() throws DataAccessException {

        db = new Database();
        db.OpenConnection();
        register = new RegisterRequest("username", "password", "none@none.com",
                "Johann", "Schmidt", "m");
    }

    @AfterEach
    public void TearDown() throws DataAccessException{
        new ClearService().Clear();
        db.CloseConnection(false);
    }

    @Test
    public void RegisterSuccess() {
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.Register(register);

        Assertions.assertNotNull(registerResult.getAuthtoken());
    }

    @Test
    public void RegisterFail() {
        RegisterRequest badRegister = new RegisterRequest(null, "password",
                "none@none.com", "John", "Smith", "m");
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.Register(badRegister);

        Assertions.assertNull(registerResult.getAuthtoken());
    }
}
