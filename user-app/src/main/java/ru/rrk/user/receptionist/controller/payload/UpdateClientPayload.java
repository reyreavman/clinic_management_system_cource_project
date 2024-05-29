package ru.rrk.user.receptionist.controller.payload;

public record UpdateClientPayload(String firstName, String lastName, String phoneNumber,
                                  String email) {
}
