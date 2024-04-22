package ru.rrk.manager.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Client {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final String phoneNumber;
    private final String email;

    public Client(int id, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;

        this.fullName = firstName + " " + lastName;
    }
}