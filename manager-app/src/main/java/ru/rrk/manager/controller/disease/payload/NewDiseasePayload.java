package ru.rrk.manager.controller.disease.payload;

public record NewDiseasePayload(
        int code,
        String description
) {
}
