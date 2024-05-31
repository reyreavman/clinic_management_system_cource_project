package ru.rrk.user.receptionist.controller.appointment.payload;

import java.time.LocalDate;
import java.time.LocalTime;

public record NewAppointmentDetailsPayload(
        Integer petId,
        Integer vetId,
        LocalDate date,
        LocalTime time,
        String description,
        Integer checkupId,
        Integer receptionistId
) {
}