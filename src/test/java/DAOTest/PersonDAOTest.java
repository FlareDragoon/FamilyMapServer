package DAOTest;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.PersonModel;
import org.junit.jupiter.api.*;

import java.sql.Connection;

public class PersonDAOTest {
    private Database db;
    private PersonDAO personDAO;
    private PersonModel person1;
    private PersonModel person2;

    @BeforeEach
    public void SetUp() throws DataAccessException{

        db = new Database();

        Connection conn = db.GetConnection();
        db.ClearTables();
        personDAO = new PersonDAO(conn);

        person1 = new PersonModel("123", "user1", "John",
            "Davis", "m", "papa", "momma", "honey");
        person2 = new PersonModel("234", "user2", "Mary",
            "Davis", "f", null, null, null);


    }

    @AfterEach
    public void TearDown() throws DataAccessException{
        db.CloseConnection(false);
    }

    @Test
    public void FindPersonSuccess() throws DataAccessException {
        personDAO.InsertPerson(person1);
        PersonModel comparePerson = personDAO.FindPerson("123");
        Assertions.assertEquals(person1, comparePerson);
    }

    @Test
    public void FindPersonFail() throws DataAccessException {
        Assertions.assertNull(personDAO.FindPerson(null));
        Assertions.assertNull(personDAO.FindPerson("asdf"));
    }

    @Test
    public void InsertPersonSuccess() throws DataAccessException {
        personDAO.InsertPerson(person1);
        PersonModel comparePerson = personDAO.FindPerson("123");

        Assertions.assertNotNull(personDAO.FindPerson("123"));
        Assertions.assertEquals(person1, comparePerson);

        personDAO.InsertPerson(person2);
        comparePerson = personDAO.FindPerson("234");

        Assertions.assertNotNull(personDAO.FindPerson("234"));
        Assertions.assertEquals(person2, comparePerson);
    }

    @Test
    public void InsertPersonFail() throws DataAccessException {

        personDAO.InsertPerson(person1);

        Assertions.assertThrows(DataAccessException.class, ()-> {personDAO.InsertPerson(person1);});
        PersonModel badPerson = new PersonModel(null, null, null, null,
            null, null, null, null);
        Assertions.assertThrows(DataAccessException.class, ()-> {personDAO.InsertPerson(badPerson);});
    }

    @Test
    public void FindFamilySuccess() throws DataAccessException {
        personDAO.InsertPerson(person1);

        PersonModel[] family = personDAO.FindFamily("123");
        Assertions.assertEquals(1, family.length);

        PersonModel p1Spouse = new PersonModel("honey", "user0", "Serena",
            "Dawn", "f", "spouse'sDad", null, "123");
        PersonModel p1Dad = new PersonModel("papa", "papaUser", "Phil",
            "Johnson", "m", "grandPapa", "grandMama", "momma");
        PersonModel p1Mom = new PersonModel("momma", "momUser", "Kay",
            "Jones", "f", "grandPapa2", null, "papa");
        PersonModel p1Grandpa = new PersonModel("grandPapa", "user9", "Old",
            "Man Jenkins", "m", null, null, "dontAdd");
        PersonModel p1Grandma = new PersonModel("grandMama", "hello", "Jenny",
            "Davis", "f", null, null, null);
        PersonModel dontAddSpouse = new PersonModel("dontAdd", "a", "b", "c",
            "f", null, null, "grandPapa");

        personDAO.InsertPerson(p1Spouse);
        family = personDAO.FindFamily("123");
        Assertions.assertEquals(2, family.length);

        personDAO.InsertPerson(p1Dad);
        personDAO.InsertPerson(p1Mom);
        family = personDAO.FindFamily("123");
        Assertions.assertEquals(4, family.length);

        personDAO.InsertPerson(p1Grandpa);
        personDAO.InsertPerson(dontAddSpouse);
        family = personDAO.FindFamily("123");
        Assertions.assertEquals(5, family.length);

        personDAO.InsertPerson(p1Grandma);
        family = personDAO.FindFamily("123");
        Assertions.assertEquals(6, family.length);
    }

    @Test
    public void FindFamilyFail() {
        Assertions.assertThrows(DataAccessException.class, ()-> {personDAO.FindFamily(null);});
        Assertions.assertThrows(DataAccessException.class, ()-> {personDAO.FindFamily("asdf");});
    }

    @Test
    public void FindPeopleSuccess() throws DataAccessException {
        personDAO.InsertPerson(person1);
        PersonModel[] people = personDAO.FindAllPeople();
        Assertions.assertEquals(1, people.length);

        personDAO.InsertPerson(person2);
        people = personDAO.FindAllPeople();
        Assertions.assertEquals(2, people.length);
        }

    @Test
    public void DeleteAllPeopleTest() throws DataAccessException {
        personDAO.InsertPerson(person1);
        personDAO.InsertPerson(person2);
        Assertions.assertNotNull(personDAO.FindPerson("123"));
        Assertions.assertNotNull(personDAO.FindPerson("234"));

        personDAO.DeleteAllPeople();
        Assertions.assertNull(personDAO.FindPerson("123"));
        Assertions.assertNull(personDAO.FindPerson("234"));
    }

    @Test
    public void DeletePersonTest() throws DataAccessException {
        personDAO.InsertPerson(person1);
        personDAO.InsertPerson(person2);

        personDAO.DeletePerson("123");
        Assertions.assertNull(personDAO.FindPerson("123"));

        PersonModel comparePerson = personDAO.FindPerson("234");
        Assertions.assertEquals(person2, comparePerson);
    }

}