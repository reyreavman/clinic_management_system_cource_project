package ru.rrk.manager.controller.payload;

public record UpdateClientPayload(String firstName, String lastName, String phoneNumber, String email, String fullName) {
    public UpdateClientPayload(String firstName, String lastName, String phoneNumber, String email) {
        this(firstName, lastName, phoneNumber, email, firstName + " " + lastName);
    }
}
