package ru.rrk.manager.restClients.appointment.result.result;

import ru.rrk.manager.entity.appointments.AppointmentResult;

import java.util.List;
import java.util.Optional;

public interface AppointmentResultRestClient {
    List<AppointmentResult> findAllAppointmentResults();

    AppointmentResult createAppointmentResult(Integer currentAppointmentId, Integer nextAppointmentId, Integer stateId, Integer diagnosisId, String advice, String prescription);

    Optional<AppointmentResult> findAppointmentResult(Integer appointmentResult);

    void updateAppointmentResult(Integer appointmentResultId, Integer currentAppointment, Integer nextAppointment, Integer state, Integer diagnosis, String advice, String prescription);

    void deleteAppointment(Integer appointmentResultId);
}
