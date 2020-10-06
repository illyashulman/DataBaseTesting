package utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseUtility {
    static final String CREATE_PERSONS_TABLE = "create table if not exists persons (person_id serial primary key, surname varchar(50) not null, name varchar(30) not null, birthdate timestamp not null, email varchar(80) not null, created_on timestamp not null, updated_at timestamp)";
    static final String DELETE_PERSONS_TABLE = "drop table persons";
    static final String DELETE_ALL_DATA_FROM_PERSONS_TABLE = "delete from persons";

    static final String CREATE_ADVERTISE_TABLE = "create table advertises (advertise_id serial primary key, header varchar(30), structure varchar(300), " +
            "person_id integer references persons(person_id), created_on timestamp, updated_on timestamp)";
    static final String DELETE_ADVERTISE_TABLE = "drop table advertises";
    static final String DELETE_ALL_DATA_FROM_ADVERTISES_TABLE = "delete from advertises";


        public static void createPersonsTable() {
            try (Connection connection = PostgresConnection.getConnection();
                 Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(CREATE_PERSONS_TABLE);
                System.out.println("New table successfully created! ");

            } catch (SQLException e) {
                System.out.println("Connection failure!");
                e.getStackTrace();
            }
        }

        public static void deletePersonsTable() {
            try (Connection connection = PostgresConnection.getConnection();
                 Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(DELETE_PERSONS_TABLE);
                System.out.println("Table is successfully deleted! ");

            } catch (SQLException e) {
                System.out.println("Connection failure!");
                e.getStackTrace();
            }
        }

    public static void deleteDataFromPersonsTable() {
        try (Connection connection = PostgresConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(DELETE_ALL_DATA_FROM_PERSONS_TABLE);
            System.out.println("Data is successfully deleted! ");

        } catch (SQLException e) {
            System.out.println("Connection failure!");
            e.getStackTrace();
        }
    }


        public static void createAdvertisesTable() {
            try (Connection connection = PostgresConnection.getConnection();
                 Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(CREATE_ADVERTISE_TABLE);
                System.out.println("New table successfully created! ");

            } catch (SQLException e) {
                System.out.println("Connection failure!");
                e.getStackTrace();
            }
        }

        public static void deleteAdvertisesTable() {
            try (Connection connection = PostgresConnection.getConnection();
                 Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(DELETE_ADVERTISE_TABLE);
                System.out.println("Table successfully deleted! ");

            } catch (SQLException e) {
                System.out.println("Connection failure!");
                e.getStackTrace();
            }
        }

    public static void deleteDataFromAdvertisesTable() {
        try (Connection connection = PostgresConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(DELETE_ALL_DATA_FROM_ADVERTISES_TABLE);
            System.out.println("Data is successfully deleted! ");

        } catch (SQLException e) {
            System.out.println("Connection failure!");
            e.getStackTrace();
        }
    }
}
