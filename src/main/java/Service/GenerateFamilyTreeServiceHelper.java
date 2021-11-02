package Service;

import Data.*;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import Model.EventModel;
import Model.PersonModel;

import java.util.Date;
import java.util.Random;

public class GenerateFamilyTreeServiceHelper extends BaseService {
    private String username;
    private Database db;

    public GenerateFamilyTreeServiceHelper(String username, Database db) {
        this.username = username;
        this.db = db;
    }
    /**
     * Generates new family tree
     * @param person user to add family for
     * @param generations generations of family tree to add
     */
    public void GenerateFamilyTree(PersonModel person, int generations) {
        try {
            int birthYear = GenerateUserBirthYear();

            GenerateParents(person, generations, birthYear);

            new PersonDAO(db.GetConnection()).InsertPerson(person);
            GenerateBirthEvent(person, birthYear);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }

    private void GenerateParents(PersonModel person, int generations, int birthYear) {
        if (generations > 0) {
            int fatherBirthYear = GenerateParentBirthYear(birthYear);

            person.setFatherID(NewUUID());
            PersonModel father = GenerateFather(generations, person.getFatherID(), fatherBirthYear);

            father.setSpouseID(NewUUID());

            int motherBirthYear = GenerateParentBirthYear(birthYear);

            person.setMotherID(father.getSpouseID());
            PersonModel mother = GenerateMother(generations, person.getMotherID(), motherBirthYear);

            mother.setSpouseID(father.getPersonID());

            try {
                new PersonDAO(db.GetConnection()).InsertPerson(father);
                new PersonDAO(db.GetConnection()).InsertPerson(mother);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }

            int marriageYear = GenerateMarriageYear(fatherBirthYear, motherBirthYear);
            GenerateMarriageEvent(father, mother, marriageYear);
        }
    }

    public PersonModel GenerateFather(int generations, String fatherID, int birthYear) {

        String[] firstNames =  DeserializeData.getDataCache().getMaleNamesData().getMaleNames();
        int firstNameIndex = new Random().nextInt(firstNames.length);

        String firstName = firstNames[firstNameIndex];
        String lastName = GenerateSurname();

        PersonModel father = new PersonModel(fatherID, username, firstName, lastName,
            "m", null, null, null);

        GenerateBirthEvent(father, birthYear);

        int deathYear = GenerateDeathYear(birthYear);
        GenerateDeathEvent(father, deathYear);

        GenerateParents(father, (generations - 1), birthYear);

        return father;
    }

    public PersonModel GenerateMother(int generations, String motherID, int birthYear) {
        String[] firstNames = DeserializeData.getDataCache().getFemaleNamesData().getFemaleNames();
        int firstNameIndex = new Random().nextInt(firstNames.length);

        String firstName = firstNames[firstNameIndex];
        String lastName = GenerateSurname();

        PersonModel mother = new PersonModel(motherID, username, firstName, lastName,
            "f", null, null, null);

        GenerateBirthEvent(mother, birthYear);

        int deathYear = GenerateDeathYear(birthYear);
        GenerateDeathEvent(mother, deathYear);

        GenerateParents(mother, (generations - 1), birthYear);

        return mother;
    }

    public String GenerateSurname() {
        String[] surnames = DeserializeData.getDataCache().getSurnamesData().getSurnames();
        int surnameIndex = new Random().nextInt(surnames.length);
        return surnames[surnameIndex];
    }

    public int GenerateUserBirthYear() {
        int MAX_AGE = 25;
        int MIN_AGE = 13;

        int age = new Random().nextInt((MAX_AGE - MIN_AGE) + 1) + MIN_AGE;
        return (GetCurrentYear() - age);
    }
    public int GenerateParentBirthYear(int childBirthYear) {
        int MAX_AGE = 40;
        int MIN_AGE = 17;

        int age = new Random().nextInt((MAX_AGE - MIN_AGE) + 1) + MIN_AGE;
        return (childBirthYear - age);
    }

    public int GenerateMarriageYear(int fatherBirthYear, int motherBirthYear) {
        int MAX_AGE = 30;
        int MIN_AGE = 18;

        int marriageAge = new Random().nextInt((MAX_AGE - MIN_AGE) + 1) + MIN_AGE;
        int marriageYear = marriageAge + motherBirthYear;

        while( (marriageYear - fatherBirthYear) < 18) {
            marriageYear++;
        }
        while( (marriageYear - motherBirthYear) < 18) {
            marriageYear++;
        }

        return marriageYear;
    }

    public int GenerateDeathYear(int birthYear) {
        int MAX_AGE = 100;
        int MIN_AGE = 60;

        int age = new Random().nextInt((MAX_AGE - MIN_AGE) + 1) + MIN_AGE;
        return (birthYear + age);
    }

    public int GetCurrentYear() {
        Date date = new Date();
        return (date.getYear() + 1900);
    }

    private Location GenerateLocation() {
        Location[] locations = DeserializeData.getDataCache().getLocationData().getLocations();
        int index = new Random().nextInt(locations.length);
        return locations[index];
    }

    private void GenerateMarriageEvent(PersonModel father, PersonModel mother, int marriageYear) {
        try {
            Location location = GenerateLocation();

            EventModel fathersMarriage = new EventModel(NewUUID(), username,
                    father.getPersonID(), location.getLatitude(),
                    location.getLongitude(), location.getCountry(),
                    location.getCity(), "MARRIAGE", marriageYear);
            EventModel mothersMarriage = new EventModel(NewUUID(), username,
                    mother.getPersonID(), location.getLatitude(),
                    location.getLongitude(), location.getCountry(),
                    location.getCity(), "MARRIAGE", marriageYear);

            new EventDAO(db.GetConnection()).InsertEvent(fathersMarriage);
            new EventDAO(db.GetConnection()).InsertEvent(mothersMarriage);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void GenerateBirthEvent(PersonModel person, int birthYear) {
        try {
            Location location = GenerateLocation();

            EventModel birthEvent = new EventModel(NewUUID(), username,
                    person.getPersonID(), location.getLatitude(),
                    location.getLongitude(), location.getCountry(),
                    location.getCity(), "BIRTH", birthYear);

            new EventDAO(db.GetConnection()).InsertEvent(birthEvent);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void GenerateDeathEvent(PersonModel person, int deathYear) {
        try {
            Location location = GenerateLocation();

            EventModel birthEvent = new EventModel(NewUUID(), username,
                    person.getPersonID(), location.getLatitude(),
                    location.getLongitude(), location.getCountry(),
                    location.getCity(), "DEATH", deathYear);

            new EventDAO(db.GetConnection()).InsertEvent(birthEvent);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
