package ru.rrk.users.receptionist.controller.client.payload;

public record NewClientPayload(
        String firstName,
        String lastName,
        String phoneNumber,
        String email
) {
}
