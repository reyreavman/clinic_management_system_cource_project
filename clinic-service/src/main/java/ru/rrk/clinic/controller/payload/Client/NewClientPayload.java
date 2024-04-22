package ru.rrk.clinic.controller.payload.Client;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewClientPayload(
        @NotNull(message = "{clinic.clients.create.errors.firstName_is_null}")
        @Size(min = 2, max = 50, message = "{clinic.clients.create.errors.firstName_size_is_invalid}")
        String firstName,

        @Nullable
        @Size(max = 50, message = "{clinic.clients.create.errors.lastName_size_is_invalid}")
        String lastName,

        @NotNull(message = "{clinic.clients.create.errors.phoneNumber_is_null}")
        @Size(max = 10, message = "{clinic.clients.create.errors.phoneNumber_size_is_invalid}")
        String phoneNumber,

        @Nullable
        @Size(max = 255, message = "{clinic.clients.create.errors.email_size_is_invalid}")
        String email
) {
}
