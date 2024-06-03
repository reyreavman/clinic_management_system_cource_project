package ru.rrk.common.viewModels.vet;

public record VetView(
        int id,
        String firstName,
        String lastName,
        String fullName,
        String speciality) {
}
