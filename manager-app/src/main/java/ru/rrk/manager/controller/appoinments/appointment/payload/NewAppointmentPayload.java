package ru.rrk.manager.controller.appoinments.appointment.payload;

import ru.rrk.manager.entity.Receptionist;

import java.time.LocalDate;
import java.time.LocalTime;

public record NewAppointmentPayload(
        Integer petId,
        Integer vetId,
        LocalDate date,
        LocalTime time,
        String description,
        Integer checkupId,
        Integer receptionistId
) {
}
