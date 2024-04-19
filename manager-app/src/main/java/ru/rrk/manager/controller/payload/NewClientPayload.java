package ru.rrk.manager.controller.payload;

public record NewClientPayload(String firstName, String lastName, String phoneNumber, String email, String fullName) {
    public NewClientPayload(String firstName, String lastName, String phoneNumber, String email) {
        this(firstName, lastName, phoneNumber, email, firstName + " " + lastName);
    }
}
