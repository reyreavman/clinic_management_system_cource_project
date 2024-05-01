package ru.rrk.clinic.controller.speciality.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewSpecialityPayload(
        @NotNull
        @Size(min = 1, max = 50)
        String name
) {
}
