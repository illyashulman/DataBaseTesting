package model;

import java.time.LocalDate;
import java.util.Objects;


public class Advertise {
    private int id;
    private String header;
    private String structure;
    private LocalDate dateOfCreation;
    private Person person;

    public Advertise() {
    }

    public Advertise(int id, String header, String structure, LocalDate dateOfCreation, Person person) {
        this.id = id;
        this.header = header;
        this.structure = structure;
        this.dateOfCreation = dateOfCreation;
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertise advertise = (Advertise) o;
        return id == advertise.id &&
                Objects.equals(header, advertise.header) &&
                Objects.equals(structure, advertise.structure) &&
                Objects.equals(dateOfCreation, advertise.dateOfCreation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, header, structure, dateOfCreation);
    }

    @Override
    public String toString() {
        return "Advertise{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", structure='" + structure + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", person=" + person +
                '}';
    }
}
