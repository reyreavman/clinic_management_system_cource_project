package ru.rrk.clinic.controller.checkup.checkup.payload;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record NewCheckupPayload(
        @NotNull
        LocalDate date,
        @NotNull
        LocalTime time,
        @NotNull
        Integer petId,
        @NotNull
        Integer vetId,
        @NotNull
        Integer checkupTypeId,
        @NotNull
        Integer checkupStateId) {
}
