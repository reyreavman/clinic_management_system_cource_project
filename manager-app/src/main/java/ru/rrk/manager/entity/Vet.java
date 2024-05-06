package ru.rrk.manager.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Vet {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final Speciality speciality;

    public Vet(int id, String firstName, String lastName, Speciality speciality) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;

        this.fullName = firstName + " " + lastName;
    }
}
