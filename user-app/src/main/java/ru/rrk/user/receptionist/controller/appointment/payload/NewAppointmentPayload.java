package ru.rrk.user.receptionist.controller.appointment.payload;

import java.time.LocalDate;
import java.time.LocalTime;

public record NewAppointmentPayload(
        LocalDate date,
        LocalTime time,
        int vetId,
        String description
) {
}
