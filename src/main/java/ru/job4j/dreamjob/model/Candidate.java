package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate {

    private int id;

    private String name;

    private int age;

    private String description;

    public Candidate(int id, String name, int age, String description) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id && age == candidate.age && Objects.equals(name, candidate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
