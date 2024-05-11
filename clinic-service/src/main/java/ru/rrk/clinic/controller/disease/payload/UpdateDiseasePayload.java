package ru.rrk.clinic.controller.disease.payload;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateDiseasePayload(
        @NotNull(message = "{clinic.diseases.update.errors.code_is_nul}")
        int code,

        @Nullable
        @Size(max = 255)
        String description
) {
}
