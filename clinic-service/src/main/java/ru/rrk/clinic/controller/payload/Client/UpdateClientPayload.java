package ru.rrk.clinic.controller.payload.Client;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateClientPayload(
        @NotNull(message = "{clinic.clients.update.errors.firstName_is_null}")
        @Size(min = 2, max = 50, message = "{clinic.clients.update.errors.firstName_size_is_invalid}")
        String firstName,

        @Nullable
        @Size(max = 50, message = "{clinic.clients.update.errors.lastName_size_is_invalid}")
        String lastName,

        @NotNull(message = "{clinic.clients.update.errors.phoneNumber_is_null}")
        @Size(max = 10, message = "{clinic.clients.update.errors.phoneNumber_size_is_invalid}")
        String phoneNumber,

        @Nullable
        @Size(max = 255, message = "{clinic.clients.update.errors.email_size_is_invalid}")
        String email
) {
}
