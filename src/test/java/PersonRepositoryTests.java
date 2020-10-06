import Exceptions.UniqueException;
import jdbc.PersonRepository;
import model.Person;
import org.testng.Assert;
import org.testng.annotations.*;
import utility.DataBaseUtility;
import utility.JsonTest;
import utility.PersonData;
import utility.PostgresConnection;

import java.sql.SQLException;
import java.util.List;


public class PersonRepositoryTests {

    @BeforeSuite
    void fillTableWithTestData() throws SQLException {
        int length = PersonRepository.getAllPersons().size();
        List<Person> people = PersonRepository.getAllPersons();
        PersonData[] personDataArray = new PersonData[length];
        for(int i=0;i<length;i++){
            PersonData personData = new PersonData();
            personData.setPerson(people.get(i));
            personDataArray[i] = personData;
        }
        JsonTest.writePersonDataIntoJson(personDataArray);
        DataBaseUtility.deleteDataFromPersonsTable();
        PersonData[] personData = JsonTest.getPersonDataFromJson();
        for (int i = 0; i < personData.length; i++) {
            PersonRepository.addPerson(personData[i].getPerson());
        }

    }

    @DataProvider(name = "personData")
    public static Object[][] getPersonData() {
        PersonData[] personData = JsonTest.getPersonDataFromJson();
        Object[][] objects = new Object[personData.length][];
        for (int i = 0; i < personData.length; i++) {
            objects[i] = new Object[]{personData[i].getPerson()};

        }
        return objects;
    }

    @DataProvider(name = "dataFromPersonTable")
    public static Object[][] getPersonDataFromDataBase() throws SQLException {
        int length = PersonRepository.getAllPersons().size();
        PersonData[] personDataArray = new PersonData[length];
       List<Person> people = PersonRepository.getAllPersons();

        for(int i=0;i<length;i++){
            PersonData personData = new PersonData();
            personData.setPerson(people.get(i));
            personDataArray[i] = personData;
        }
        Object[][] objects = new Object[personDataArray.length][];
        for (int i = 0; i < personDataArray.length; i++) {
            objects[i] = new Object[]{personDataArray[i].getPerson()};

        }
        return objects;
    }

    @Test(dataProvider = "personData", expectedExceptions = {UniqueException.class})
    void testAddPerson(Person person) throws SQLException {
        PersonRepository.addPerson(person);

    }
    @Test(dataProvider = "dataFromPersonTable")
    void testUpdatePerson(Person person) throws SQLException {
        person.setSurname("Henderson");
        PersonRepository.updatePerson(person);
      Assert.assertEquals(PersonRepository.getPersonById(person.getId()).getSurname(),"Henderson");
    }
    @Test
    void testGetAllPersons() throws SQLException {
        Assert.assertEquals(PersonRepository.getAllPersons().size(), getPersonData().length);
    }

    @Test(dataProvider = "personData")
    void testGetPersonById(Person person) throws SQLException {

        PersonRepository.getPersonById(PersonRepository.getPersonId(person));
    }

    @Test(dataProvider = "personData")
    void testGetPersonsAdvertises(Person person) throws SQLException {
        Assert.assertEquals(PersonRepository.getAllPersonAdvertises(person).size(), 0);
    }

    @AfterSuite
    void deleteTestDataFromTable() throws SQLException {

        DataBaseUtility.deleteDataFromPersonsTable();
        PersonData[]personData = JsonTest.recoverPersonTableData();
        for(int i=0;i< personData.length;i++){
            PersonRepository.addPerson(personData[i].getPerson());
        }
    }

}
