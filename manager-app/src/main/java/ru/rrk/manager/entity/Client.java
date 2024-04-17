package ru.rrk.manager.entity;

public record Client(int id, String firstName, String lastName, String phoneNumber, String email, String fullName) {
    Client(int id, String firstName, String lastName, String phoneNumber, String email) {
        this(id, firstName, lastName, phoneNumber, email, firstName + " " + lastName);
    }
}