package ru.rrk.user.receptionist.controller.client.payload;

public record NewClientPayload(
        String firstName,
        String lastName,
        String phoneNumber,
        String email
) {
}
