package ServiceTest;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.FillRequest;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.FillResult;
import Result.RegisterResult;
import Service.ClearService;
import Service.FillService;
import Service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FillTest {
    private Database db;
    private RegisterRequest register;
    private FillRequest fillRequest;

    @BeforeEach
    public void SetUp() throws DataAccessException {

        db = new Database();
        db.OpenConnection();
        register = new RegisterRequest("username", "password", "none@none.com",
                "Johann", "Schmidt", "m");
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.Register(register);

        fillRequest = new FillRequest("username", 2);
    }

    @AfterEach
    public void TearDown() throws DataAccessException{
        new ClearService().Clear();
        db.CloseConnection(false);
    }

    @Test
    public void FillTest() {
        FillService service = new FillService();
        FillResult result = service.FillTree(fillRequest);

        Assertions.assertTrue(result.isSuccess());
    }
}
