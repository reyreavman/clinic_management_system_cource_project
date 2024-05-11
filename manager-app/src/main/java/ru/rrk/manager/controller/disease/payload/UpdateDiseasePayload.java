package ru.rrk.manager.controller.disease.payload;

public record UpdateDiseasePayload(
        int code,
        String description
) {
}
