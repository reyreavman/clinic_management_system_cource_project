package ru.rrk.clinic.controller.diagnosis.payload;

public record NewDiagnosisPayload(
        Integer diseaseId, String description) {
}
