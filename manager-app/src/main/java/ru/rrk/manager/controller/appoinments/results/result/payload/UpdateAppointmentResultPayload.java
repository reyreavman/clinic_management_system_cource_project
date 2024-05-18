package ru.rrk.manager.controller.appoinments.results.result.payload;

public record UpdateAppointmentResultPayload(
        Integer currentAppointmentId,
        Integer nextAppointmentId,
        Integer stateId,
        Integer diagnosisId,
        String advice,
        String prescription
) {
}
