package ServiceTest;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Request.*;
import Result.*;
import Service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetAllFamilyEventsTest {
    private Database db;
    private LoadRequest load;

    @BeforeEach
    public void SetUp() throws DataAccessException {

        db = new Database();
        db.OpenConnection();
        UserModel[] users = {
                new UserModel("user1", "password", "none", "John",
                        "Davis", "m", "abc"),
        };
        PersonModel[] people = {
                new PersonModel("abc", "user1", "John",
                        "Davis", "m", "papa", "momma", "bcd"),
        };
        EventModel[] events = {
                new EventModel("abc", "user1", "abc", 20f,
                        30f, "USA", "Farmington", "birth", 2001),
                new EventModel("bca", "user1", "abc", 20f,
                        30f, "USA", "Farmington", "marriage", 2020),
                new EventModel("cab", "user1", "abc", 20f,
                        30f, "USA", "Farmington", "death", 2021),
        };

        load = new LoadRequest(users, people, events);
        LoadService loadService = new LoadService();
        LoadResult result = loadService.Load(load);
    }

    @AfterEach
    public void TearDown() throws DataAccessException{
        new ClearService().Clear();
        db.CloseConnection(false);
    }

    @Test
    public void GetEventsSuccess() {
        LoginRequest loginRequest = new LoginRequest("user1", "password");
        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.Login(loginRequest);

        GetAllFamilyEventsRequest request = new GetAllFamilyEventsRequest(loginResult.getAuthtoken());
        GetAllFamilyEventsService service = new GetAllFamilyEventsService();
        GetAllFamilyEventsResult result = service.FindEvents(request);

        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    public void GetFamilyFail() {
        GetAllFamilyEventsRequest badRequest = new GetAllFamilyEventsRequest(null);
        GetAllFamilyEventsService service = new GetAllFamilyEventsService();
        GetAllFamilyEventsResult result = service.FindEvents(badRequest);

        Assertions.assertFalse(result.isSuccess());
    }
}
