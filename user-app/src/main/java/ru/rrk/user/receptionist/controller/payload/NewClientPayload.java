package ru.rrk.user.receptionist.controller.payload;

public record NewClientPayload(
        String firstName,
        String lastName,
        String phoneNumber,
        String email
) {
}
