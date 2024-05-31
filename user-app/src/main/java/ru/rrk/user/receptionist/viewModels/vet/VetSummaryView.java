package ru.rrk.user.receptionist.viewModels.vet;

public record VetSummaryView(
        int id,
        String firstName,
        String lastName,
        String fullName,
        String speciality) {
}
