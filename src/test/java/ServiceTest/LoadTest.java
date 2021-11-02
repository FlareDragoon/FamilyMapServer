package ServiceTest;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Request.LoadRequest;
import Request.RegisterRequest;
import Result.LoadResult;
import Service.ClearService;
import Service.LoadService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadTest {
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
                new PersonModel("bcd", "user2", "Serena",
                        "Dawn", "f", "spouse'sDad", null, "abc"),
                new PersonModel("papa", "papaUser", "Phil",
                        "Johnson", "m", "grandPapa", "grandMama", "momma"),
                new PersonModel("momma", "momUser", "Kay",
                        "Jones", "f", "grandPapa2", null, "papa"),
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
    }

    @AfterEach
    public void TearDown() throws DataAccessException{
        new ClearService().Clear();
        db.CloseConnection(false);
    }

    @Test
    public void LoadSuccess() {
        LoadService loadService = new LoadService();
        LoadResult result = loadService.Load(load);

        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    public void LoadFail() {
        LoadRequest badLoad = new LoadRequest(null, null, null);

        LoadService loadService = new LoadService();
        LoadResult result = loadService.Load(badLoad);

        Assertions.assertFalse(result.isSuccess());
    }
}
