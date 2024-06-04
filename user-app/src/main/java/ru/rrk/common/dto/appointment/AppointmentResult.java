package ru.rrk.common.dto.appointment;

import ru.rrk.common.dto.Diagnosis;

public record AppointmentResult(
        int id,
        Appointment currentAppointment,
        Appointment nextAppointment,
        AppointmentResultState state,
        Diagnosis diagnosis,
        String advice,
        String prescription
) {
}
