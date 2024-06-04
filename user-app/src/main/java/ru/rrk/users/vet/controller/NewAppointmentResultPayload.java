package ru.rrk.users.vet.controller;

public record NewAppointmentResultPayload(
        Integer currentAppointmentId,
        Integer nextAppointmentId,
        Integer stateId,
        Integer diagnosisId,
        String advice,
        String prescription
) {
}
