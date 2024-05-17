package ru.rrk.manager.entity.appointments;

import ru.rrk.manager.entity.Diagnosis;

public record AppointmentResult(
        int appointmentResultId,
        Appointment currentAppointment,
        Appointment nextAppointment,
        AppointmentResultState state,
        Diagnosis diagnosis,
        String advice,
        String prescription
) {
}
