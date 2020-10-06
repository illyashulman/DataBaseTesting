package utility;

import model.Person;


public class PersonData {
    private Person person;

    @Override
    public String toString() {
        return "PersonData{" +
                "person=" + person +
                '}';
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
