package ru.rrk.user.receptionist.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Receptionist {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String fullName;

    public Receptionist(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName.concat(" ").concat(lastName);
    }
}
