package ru.rrk.manager.controller.appoinments.results.result.payload;

public record NewAppointmentResultPayload(
        Integer currentAppointmentId,
        Integer nextAppointmentId,
        Integer stateId,
        Integer diagnosisId,
        String advice,
        String prescription
) {
}
