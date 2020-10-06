package utility;

import com.google.gson.*;
import jdbc.AdvertiseRepository;
import jdbc.PersonRepository;
import model.Advertise;
import model.Person;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;


public class JsonTest {
    static final String PERSON_JSON = "/Users/illyashulman/SoftServeCourse/src/test/resources/personData";
    static final String ADVERTISE_JSON = "/Users/illyashulman/SoftServeCourse/src/test/resources/advertiseData";
    static final String PERSON_TABLE_DATA = "/Users/illyashulman/SoftServeCourse/src/test/resources/PersonDataInput";
    static final String ADVERTISE_TABLE_DATA = "/Users/illyashulman/SoftServeCourse/src/test/resources/advertiseTableData";
    public static PersonData[] getPersonDataFromJson() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(PERSON_JSON));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        PersonData[] personData = gson.fromJson(reader, PersonData[].class);
        return personData;
    }

    public static AdvertiseData[] getAdvertiseDataFromJson() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(ADVERTISE_JSON));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        AdvertiseData[] advertiseData = gson.fromJson(reader, AdvertiseData[].class);


        return advertiseData;
    }

    public static AdvertiseData[] recoverAdvertiseTableDataFromJson() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(ADVERTISE_TABLE_DATA));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        AdvertiseData[] advertiseData = gson.fromJson(reader, AdvertiseData[].class);


        return advertiseData;
    }
    public static void writeAdvertiseDataIntoJson(AdvertiseData[] advertiseData){
        Gson gson = new Gson();
        StringBuilder jsonString = new StringBuilder();
        jsonString.append('[');
        try {
            for(int i=0;i< advertiseData.length;i++)
                jsonString.append(gson.toJson(advertiseData[i])).append(',');
            jsonString.deleteCharAt(jsonString.length()-1).append(']');
            FileWriter fileWriter = new FileWriter(ADVERTISE_TABLE_DATA);

            fileWriter.write(String.valueOf(jsonString));
            fileWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void writePersonDataIntoJson(PersonData[] personData){
        Gson gson = new Gson();
        StringBuilder jsonString = new StringBuilder();
        jsonString.append('[');
        try {
            for(int i=0;i< personData.length;i++)
            jsonString.append(gson.toJson(personData[i])).append(',');
            jsonString.deleteCharAt(jsonString.length()-1).append(']');
            FileWriter fileWriter = new FileWriter(PERSON_TABLE_DATA);

            fileWriter.write(String.valueOf(jsonString));
            fileWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static PersonData[] recoverPersonTableData() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(PERSON_TABLE_DATA));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        PersonData[] personData = gson.fromJson(reader, PersonData[].class);
        return personData;
    }

    public static void main(String[] args) throws SQLException {

        DataBaseUtility.deleteDataFromPersonsTable();
        Person person = new Person(0,"Victor","Denysenko", LocalDate.of(1960,6,5),"vic.denys1960@rambler.ru");
        PersonRepository.addPerson(person);
    }
}







