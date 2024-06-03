package ru.rrk.users.receptionist.controller.client.payload;

public record UpdateClientPayload(
        String firstName,
        String lastName,
        String phoneNumber,
        String email) {
}
