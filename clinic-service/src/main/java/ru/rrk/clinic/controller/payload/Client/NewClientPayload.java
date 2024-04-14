package ru.rrk.clinic.controller.payload.Client;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewClientPayload(
        @NotNull(message = "{clinic.clients.create.errors.first_name_is_null}")
        @Size(max = 50)
        String firstName,

        @Nullable
        @Size(max = 50, message = "{clinic.clients.create.errors.last_name_size_is_invalid}")
        String lastName,

        @NotNull(message = "{clinic.clients.create.errors.phone_number_is_null}")
        @Size(max = 20)
        String phoneNumber,

        @Nullable
        @Size(max = 255)
        String email
) {
}
