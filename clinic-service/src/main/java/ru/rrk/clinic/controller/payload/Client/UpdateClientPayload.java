package ru.rrk.clinic.controller.payload.Client;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateClientPayload(
        @NotNull(message = "{clinic.clients.update.errors.first_name_is_null}")
        @Size(max = 50)
        String firstName,

        @Nullable
        @Size(max = 50, message = "{clinic.clients.update.errors.last_name_size_is_invalid}")
        String lastName,

        @NotNull(message = "{clinic.clients.update.errors.phone_number_is_null}")
        @Size(max = 20)
        String phoneNumber,

        @Nullable
        @Size(max = 255)
        String email
) {
}
