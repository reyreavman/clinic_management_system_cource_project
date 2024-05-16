package ru.rrk.clinic.controller.appointment.appointment.payload;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateAppointmentPayload(
        @NotNull
        Integer petId,
        @NotNull
        Integer vetId,
        @NotNull
        LocalDate date,
        @NotNull
        LocalTime time,
        String description,
        Integer checkupId,
        @NotNull
        Integer receptionistId
) {
}
