package jdbc;

import Exceptions.UniqueException;
import model.Advertise;
import model.Person;
import utility.PostgresConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    static final String GET_ALL_PERSONS = "SELECT *  FROM persons";

    public static Person addPerson(Person person) throws SQLException {
        if (isPersonPresent(person)) {
            throw new UniqueException("This person is already in the table");
        }
        String ADD_NEW_PERSON = "INSERT INTO persons (surname, name, birthdate, email, created_on)"
                + "values(?,?,?,?,?)";

        try (Connection connection = PostgresConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_PERSON, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, person.getSurname());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setObject(3, person.getDateOfBirth());
            preparedStatement.setString(4, person.getEmail());
            preparedStatement.setObject(5, LocalDateTime.now());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                person.setId(rs.getInt("person_id"));
            }
            System.out.println("Person is successfully added! ");


        }

        return person;
    }

    public static boolean updatePerson(Person person) throws SQLException {

        String EDIT_PERSON = "UPDATE persons SET surname=?, name=?, birthdate=?,email=?,updated_at=?  " +
                " WHERE person_id = ? ;";
        try (Connection connection = PostgresConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(EDIT_PERSON);
            preparedStatement.setString(1, person.getSurname());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setObject(3, person.getDateOfBirth());
            preparedStatement.setString(4, person.getEmail());
            preparedStatement.setObject(5, LocalDateTime.now());
            preparedStatement.setInt(6, person.getId());

            return (preparedStatement.executeUpdate() != 0);
        }
    }

    public static void deletePerson(Person person) throws SQLException {
        String DELETE_PERSON = "DELETE FROM persons WHERE person_id = ?";

        Connection connection = PostgresConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PERSON);
        preparedStatement.setInt(1, getPersonId(person));
        preparedStatement.execute();
        connection.close();
        System.out.println("Person is successfully deleted! ");

    }

    public static boolean isPersonPresent(Person person) throws SQLException {

        String isPersonPresent = "SELECT * FROM persons WHERE surname = '" + person.getSurname() +
                "'" + "AND name = '" + person.getName() + "'" + "AND birthdate = '" + person.getDateOfBirth() + "'";
        Connection connection = PostgresConnection.getConnection();
        Statement statement = connection.createStatement();
        try (ResultSet rs = statement.executeQuery(isPersonPresent)) {

            while (rs.next()) {
                return true;
            }
        }

        return false;
    }

    public static List<Person> getAllPersons() throws SQLException {
        List<Person> allPersons = new ArrayList<>();


        Connection connection = PostgresConnection.getConnection();
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery(GET_ALL_PERSONS);


        while (rs.next()) {
            Person person = new Person();
            person.setId(rs.getInt("person_id"));
            person.setSurname(rs.getString("surname"));
            person.setName(rs.getString("name"));
            person.setDateOfBirth(rs.getDate("birthdate").toLocalDate());
            person.setEmail(rs.getString("email"));


            allPersons.add(person);
        }

        rs.close();
        return allPersons;
    }

    public static int getPersonId(Person person) throws SQLException {

        String GET_PERSON_ID = "SELECT * FROM persons WHERE surname = '" + person.getSurname() + "'" + "AND name = '" + person.getName() + "'" + "AND birthdate = '" + person.getDateOfBirth() + "'";
        Connection connection = PostgresConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(GET_PERSON_ID);
        int personId = 0;
        while (rs.next()) {
            personId = rs.getInt("person_id");
        }
        return personId;
    }

    public static Person getPersonById(int id) throws SQLException {
        Person person = null;
        String GET_PERSON_ID = "SELECT * FROM persons WHERE person_id =" + id;
        Connection connection = PostgresConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(GET_PERSON_ID);
        while (rs.next()) {
            person = new Person(rs.getInt("person_id"), rs.getString("surname"), rs.getString("name"), rs.getDate("birthdate").toLocalDate(), rs.getString("email"));
        }

        return person;
    }


    public static List<Advertise> getAllPersonAdvertises(Person person) throws SQLException {
        List<Advertise> allAdvertises = new ArrayList<>();
        String GET_ALL_PERSONS_ADVERTISES = "select * from persons join advertises on (advertises.person_id = persons.person_id) where advertises.person_id =" + getPersonId(person);


        Connection connection = PostgresConnection.getConnection();
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery(GET_ALL_PERSONS_ADVERTISES);


        while (rs.next()) {
            Advertise advertise = new Advertise();
            advertise.setId(rs.getInt("advertise_id"));
            advertise.setHeader(rs.getString("header"));
            advertise.setStructure(rs.getString("structure"));
            advertise.setDateOfCreation(rs.getDate("created_on").toLocalDate());
            allAdvertises.add(advertise);
        }

        rs.close();
        return allAdvertises;
    }

}
