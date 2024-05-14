package ru.rrk.manager.controller.diseases.payload;

public record UpdateDiseasePayload(
        int code,
        String description
) {
}
