package ru.rrk.clinic.controller.label.payload;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record UpdateLabelPayload(
        @NotNull(message = "{clinic.labels.update.errors.value_is_null}")
        String value,
        @Nullable
        String date
) {
}
