package ru.rrk.manager.controller.clients.payload;

public record UpdateClientPayload(String firstName, String lastName, String phoneNumber, String email) {
}