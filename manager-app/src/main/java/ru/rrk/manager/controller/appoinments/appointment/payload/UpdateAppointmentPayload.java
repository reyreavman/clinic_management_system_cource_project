package ru.rrk.manager.controller.appoinments.appointment.payload;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateAppointmentPayload(
        Integer petId,
        Integer vetId,
        LocalDate date,
        LocalTime time,
        String description,
        Integer checkupId
) {
}
