package jdbc;

import Exceptions.UniqueException;
import model.Advertise;
import model.Person;
import utility.PostgresConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AdvertiseRepository {

    static final String GET_ALL_ADVERTISES = "select * from advertises";


    public static int getAdvertiseId(Advertise advertise, Person person) throws SQLException {

        String GET_PERSON_ID = "SELECT * FROM advertises WHERE header = '" + advertise.getHeader() + "'" + "AND structure = '" + advertise.getStructure() + "'" + "AND person_id = '" + PersonRepository.getPersonId(person) + "'";
        Connection connection = PostgresConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(GET_PERSON_ID);
        int advertiseId = 0;
        while (rs.next()) {
            advertiseId = rs.getInt("advertise_id");
        }
        return advertiseId;
    }

    public static boolean isAdvertisePresent(Advertise advertise, Person person) throws SQLException {

        String sql = " select*from advertises where advertise_id = '" + getAdvertiseId(advertise, person) + "' and header = '" + advertise.getHeader() + "' and structure = '" + advertise.getStructure() + "' and person_id = '" + PersonRepository.getPersonId(person) + "'";
        Connection connection = PostgresConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            return true;
        }
        return false;
    }

    private static Advertise insertNewAdvertise(Person person, Advertise advertise) {
        String ADD_NEW_ADVERTISE = "INSERT INTO advertises (header, structure, person_id, created_on)"
                + "values(?,?,?,?)";
        try (Connection connection = PostgresConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_ADVERTISE, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, advertise.getHeader());
            preparedStatement.setString(2, advertise.getStructure());
            preparedStatement.setObject(3, PersonRepository.getPersonId(person));
            preparedStatement.setObject(4, LocalDate.now());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                advertise.setId(rs.getInt("advertise_id"));
            }
            System.out.println("Advertise is successfully added! ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return advertise;
    }

    public static Advertise postAdvertise(Person person, Advertise advertise) throws SQLException {
        if (PersonRepository.isPersonPresent(person)) {
            if (isAdvertisePresent(advertise, person)) {
                throw new UniqueException("This advertise is already posted!");
            }
            insertNewAdvertise(person, advertise);

        } else {
            PersonRepository.addPerson(person);
            insertNewAdvertise(person, advertise);
        }

        return advertise;
    }

    public static boolean updateAdvertise(Advertise advertise, Person person) throws SQLException {

        String EDIT_ADVERTISE = "UPDATE advertises SET header=?, structure=?, person_id=?,created_on=?, updated_on=?" +
                " WHERE advertise_id = ? ;";
        try (Connection connection = PostgresConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(EDIT_ADVERTISE);
            preparedStatement.setString(1, advertise.getHeader());
            preparedStatement.setString(2, advertise.getStructure());
            preparedStatement.setObject(3, PersonRepository.getPersonId(person));
            preparedStatement.setObject(4, advertise.getDateOfCreation());
            preparedStatement.setObject(5, LocalDateTime.now());
            preparedStatement.setInt(6, AdvertiseRepository.getAdvertiseId(advertise, person));

            return (preparedStatement.executeUpdate() != 0);
        }
    }

    public static void deleteAdvertise(Advertise advertise, Person person) throws SQLException {
        String DELETE_PERSON = "DELETE FROM advertises WHERE advertise_id = ?";

        Connection connection = PostgresConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PERSON);
        preparedStatement.setInt(1, getAdvertiseId(advertise, person));
        preparedStatement.execute();
        connection.close();
        System.out.println("Advertise is successfully deleted! ");

    }

    public static List<Advertise> getAllAdvertises() throws SQLException {
        List<Advertise> allAdvertises = new ArrayList<>();


        Connection connection = PostgresConnection.getConnection();
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery(GET_ALL_ADVERTISES);


        while (rs.next()) {
            Advertise advertise = new Advertise();
            advertise.setId(rs.getInt("advertise_id"));
            advertise.setHeader(rs.getString("header"));
            advertise.setStructure(rs.getString("structure"));
            advertise.setDateOfCreation(rs.getDate("created_on").toLocalDate());
            advertise.setPerson(PersonRepository.getPersonById(rs.getInt("person_id")));


            allAdvertises.add(advertise);
        }

        rs.close();
        return allAdvertises;
    }

}
