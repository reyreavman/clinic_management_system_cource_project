package ru.rrk.clinic.controller.receptionist.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewReceptionistPayload(
        @NotNull(message = "{clinic.receptionist.create.errors.firstName_is_null}")
        @Size(max = 100)
        String firstName,
        @NotNull(message = "{clinic.receptionist.create.errors.lastName_is_null}")
        @Size(max = 100)
        String lastName
) {
}
