package ru.rrk.clinic.controller.vet.vet.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateVetPayload(
        @NotNull(message = "{clinic.vets.create.errors.firstName_is_null}")
        @Size(min = 1, max = 100, message = "")
        String firstName,
        @NotNull(message = "{clinic.vets.create.errors.lastName_is_null}")
        @Size(min = 1, max = 100, message = "")
        String lastName,
        @NotNull(message = "{clinic.vets.create.errors.Speciality_is_null}")
        Integer specialityId
) {
}
