package ru.rrk.manager.controller.diseases.payload;

public record NewDiseasePayload(
        int code,
        String description
) {
}
