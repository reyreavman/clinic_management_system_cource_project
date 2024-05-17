package ru.rrk.clinic.service.appointment.result;

import ru.rrk.clinic.entity.appointment.AppointmentResult;

import java.util.Optional;

public interface AppointmentResultService {
    Iterable<AppointmentResult> findAllResults();

    AppointmentResult createResult(Integer currentAppointmentId, Integer nextAppointmentId, Integer stateId, Integer diagnosisId, String advice, String prescription);

    Optional<AppointmentResult> findResult(Integer appointmentResultId);

    void updateResult(Integer appointmentResultId, Integer currentAppointmentId, Integer nextAppointmentId, Integer stateId, Integer diagnosisId, String advice, String prescription);

    void deleteResult(Integer appointmentResultId);
}
