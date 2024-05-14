package ru.rrk.clinic.service.appointment.result;

import ru.rrk.clinic.entity.appointment.AppointmentsResultState;

import java.util.Optional;

public interface AppointmentResultService {
    Iterable<AppointmentsResultState> findAllStates();

    AppointmentsResultState createState(int stateId, String state);

    Optional<AppointmentsResultState> findState(int stateId);

    void updateState(int stateId, String state);

    void deleteState(int stateId);
}
