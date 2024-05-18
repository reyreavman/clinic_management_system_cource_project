package ru.rrk.clinic.controller.appointment.results.result.payload;

import jakarta.validation.constraints.NotNull;

public record UpdateAppointmentResultPayload(
        @NotNull
        Integer currentAppointmentId,
        Integer nextAppointmentId,
        @NotNull
        Integer stateId,
        Integer diagnosisId,
        String advice,
        String prescription
) {
}
