package Request;

import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;

public class LoadRequest extends BaseRequest {
    /**
     * An array of user models to load into the database
     */
    private UserModel[] users;
    /**
     * An array of person models to load into the database
     */
    private PersonModel[] persons;
    /**
     * An array of event models to load into the database
     */
    private EventModel[] events;

    /**
     * Fills out the arrays of users, people, and events to load into the database
     * @param users an array of user objects. Have the same format as registration requests
     * @param persons an array of person models with same format as person/personID requests
     * @param events as array of event models with same format as event/eventID models
     */
    public LoadRequest(UserModel[] users, PersonModel[] persons, EventModel[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public UserModel[] getUsers() {
        return users;
    }

    public void setUsers(UserModel[] users) {
        this.users = users;
    }

    public PersonModel[] getPersons() {
        return persons;
    }

    public void setPersons(PersonModel[] persons) {
        this.persons = persons;
    }

    public EventModel[] getEvents() {
        return events;
    }

    public void setEvents(EventModel[] events) {
        this.events = events;
    }
}
