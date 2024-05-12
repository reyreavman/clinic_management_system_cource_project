package ru.rrk.clinic.controller.vet.speciality.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateSpecialityPayload(
        @NotNull
        @Size(min = 1, max = 50)
        String name
) {
}
