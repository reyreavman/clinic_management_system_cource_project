package ru.rrk.manager.controller.payload;

public record UpdateClientPayload(String firstName, String lastName, String phoneNumber, String email) {
}
