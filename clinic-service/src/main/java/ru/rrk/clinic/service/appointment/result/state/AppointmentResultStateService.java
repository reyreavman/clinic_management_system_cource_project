package ru.rrk.clinic.service.appointment.result.state;

import ru.rrk.clinic.entity.appointment.AppointmentResultState;

import java.util.Optional;

public interface AppointmentResultStateService {
    Iterable<AppointmentResultState> findAllStates();

    AppointmentResultState createState(String state);

    Optional<AppointmentResultState> findState(int stateId);

    void updateState(int stateId, String state);

    void deleteState(int stateId);
}
