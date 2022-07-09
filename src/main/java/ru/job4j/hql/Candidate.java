package ru.job4j.hql;


import ru.job4j.Constants;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private int id;

    private String name;

    private int experience;

    private BigDecimal salary;

    public static Candidate of(final String name, final int experience, final BigDecimal salary) {
        Candidate candidate = new Candidate();
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        return candidate;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(final int experience) {
        this.experience = experience;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(final BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Student: id=%s, name=%s, experience=%s, salary=%s", id, name,
                experience, salary);
    }
}
