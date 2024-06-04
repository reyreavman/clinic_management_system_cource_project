package ru.rrk.users.vet.controller;

public record NewDiagnosisPayload(
        Integer diseaseId,
        String description
) {
}
