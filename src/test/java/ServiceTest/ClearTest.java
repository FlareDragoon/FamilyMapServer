package ServiceTest;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.RegisterResult;
import Service.ClearService;
import Service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClearTest {
    private Database db;
    private RegisterRequest register;

    @BeforeEach
    public void SetUp() throws DataAccessException {

        db = new Database();
        db.OpenConnection();
        register = new RegisterRequest("username", "password", "none@none.com",
                "Johann", "Schmidt", "m");

        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.Register(register);
    }

    @AfterEach
    public void TearDown() throws DataAccessException{
        new ClearService().Clear();
        db.CloseConnection(false);
    }

    @Test
    public void ClearTest(){
        ClearService clear = new ClearService();
        ClearResult result = clear.Clear();

        Assertions.assertTrue(result.isSuccess());
    }
}
