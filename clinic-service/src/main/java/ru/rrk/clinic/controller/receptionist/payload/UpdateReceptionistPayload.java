package ru.rrk.clinic.controller.receptionist.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateReceptionistPayload(
        @NotNull(message = "{clinic.receptionist.update.errors.firstName_is_null}")
        @Size(max = 100)
        String firstName,
        @NotNull(message = "{clinic.receptionist.update.errors.lastName_is_null}")
        @Size(max = 100)
        String lastName
) {
}
