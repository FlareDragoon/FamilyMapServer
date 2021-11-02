package DAOTest;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;

import Model.EventModel;
import Model.PersonModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class EventDAOTest {
    private Database db;
    private EventDAO eventDAO;
    private EventModel event1;
    private EventModel event2;

    @BeforeEach
    public void SetUp() throws DataAccessException {

        db = new Database();

        Connection conn = db.GetConnection();
        db.ClearTables();
        eventDAO = new EventDAO(conn);

        event1 = new EventModel("123", "user1", "abc",
                12f, 12f, "USA",
                "Provo", "Birth", 2021);
        event2 = new EventModel("234", "user2", "bcd",
                23f, 23f, "Germany",
                "Frankfurt", "Marriage", 304);
    }

    @AfterEach
    public void TearDown() throws DataAccessException{
        db.CloseConnection(false);
    }

    @Test
    public void FindEventSuccess() throws DataAccessException {
        eventDAO.InsertEvent(event1);
        EventModel compareEvent = eventDAO.FindOneEvent("123");
        Assertions.assertEquals(event1, compareEvent);
    }

    @Test
    public void FindEventFail() throws DataAccessException {
        Assertions.assertNull(eventDAO.FindOneEvent(null));
        Assertions.assertNull(eventDAO.FindOneEvent("hello"));
    }

    @Test
    public void FindFamilyEventsSuccess() throws DataAccessException {
        PersonModel[] family = {
            new PersonModel("abc", "user1", "John",
                    "Davis", "m", "papa", "momma", "bcd"),
            new PersonModel("bcd", "user2", "Serena",
                        "Dawn", "f", "spouse'sDad", null, "abc"),
            new PersonModel("papa", "papaUser", "Phil",
                "Johnson", "m", "grandPapa", "grandMama", "momma"),
            new PersonModel("momma", "momUser", "Kay",
                "Jones", "f", "grandPapa2", null, "papa"),
        };

        eventDAO.InsertEvent(event1);
        eventDAO.InsertEvent(event2);

        Assertions.assertEquals(2, eventDAO.FindAllFamilyEvents(family).length);

        eventDAO.InsertEvent(new EventModel("345", "papaUser", "papa",
                3f, 36.79f, "Mexico",
                "DF", "Birth", 1934));
        eventDAO.InsertEvent(new EventModel("456", "momUser", "momma",
                59.7f, 4f, "Germany",
                "Berlin", "Marriage", 1987));

        Assertions.assertEquals(4, eventDAO.FindAllFamilyEvents(family).length);

        eventDAO.InsertEvent(new EventModel("789", "user1", "abc",
                3f, 36.79f, "Mexico",
                "DF", "Birth", 1990));

        Assertions.assertEquals(5, eventDAO.FindAllFamilyEvents(family).length);
    }

    @Test
    public void FindFamilyEventsFail() throws DataAccessException {
        PersonModel[] family = {};
        Assertions.assertThrows(NullPointerException.class, ()-> eventDAO.FindAllFamilyEvents(null));
        Assertions.assertNull(eventDAO.FindAllFamilyEvents(family));
    }

    @Test
    public void InsertEventSuccess() throws DataAccessException{
        eventDAO.InsertEvent(event1);
        EventModel compareEvent = eventDAO.FindOneEvent(event1.getEventID());

        Assertions.assertNotNull(eventDAO.FindOneEvent(compareEvent.getEventID()));
        Assertions.assertEquals(event1, compareEvent);
    }

    @Test
    public void InsertEventFail() throws DataAccessException {
        eventDAO.InsertEvent(event1);
        Assertions.assertThrows(DataAccessException.class, ()-> eventDAO.InsertEvent(event1));

        EventModel badEvent = new EventModel(null, null, null, null,
                null, null, null, null, null);
        Assertions.assertThrows(NullPointerException.class, ()-> eventDAO.InsertEvent(badEvent));
    }

    @Test
    public void DeleteAllEventsTest() throws DataAccessException {
        eventDAO.InsertEvent(event1);
        Assertions.assertNotNull(eventDAO.FindOneEvent(event1.getEventID()));

        eventDAO.DeleteAllEvents();
        Assertions.assertNull(eventDAO.FindOneEvent(event1.getEventID()));

        eventDAO.InsertEvent(event1);
        eventDAO.InsertEvent(event2);
        Assertions.assertNotNull(eventDAO.FindOneEvent(event1.getEventID()));
        Assertions.assertNotNull(eventDAO.FindOneEvent(event2.getEventID()));

        eventDAO.DeleteAllEvents();
        Assertions.assertNull(eventDAO.FindOneEvent(event1.getEventID()));
        Assertions.assertNull(eventDAO.FindOneEvent(event2.getEventID()));
    }

    @Test
    public void DeleteOneEventTest() throws DataAccessException {
        eventDAO.InsertEvent(event1);
        eventDAO.InsertEvent(event2);

        Assertions.assertNotNull(eventDAO.FindOneEvent(event1.getEventID()));
        Assertions.assertNotNull(eventDAO.FindOneEvent(event2.getEventID()));

        eventDAO.DeleteEvent(event1.getEventID());
        Assertions.assertNull(eventDAO.FindOneEvent(event1.getEventID()));
        Assertions.assertNotNull(eventDAO.FindOneEvent(event2.getEventID()));
    }

    @Test
    public void DeleteUsersEventsTest() throws DataAccessException {
        eventDAO.InsertEvent(event1);
        eventDAO.InsertEvent(new EventModel("789", "user1", "abc",
                3f, 36.79f, "Mexico", "DF", "Birth", 1990));

        Assertions.assertNotNull(eventDAO.FindOneEvent("789"));
        Assertions.assertNotNull(eventDAO.FindOneEvent("123"));

        eventDAO.DeleteUsersEvents("user1");
        Assertions.assertNull(eventDAO.FindOneEvent("789"));
        Assertions.assertNull(eventDAO.FindOneEvent("123"));
    }
}
