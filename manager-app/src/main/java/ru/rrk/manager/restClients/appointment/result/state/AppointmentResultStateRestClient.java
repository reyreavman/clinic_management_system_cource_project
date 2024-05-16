package ru.rrk.manager.restClients.appointment.result.state;

import ru.rrk.manager.entity.appointments.AppointmentResultState;

import java.util.List;
import java.util.Optional;

public interface AppointmentResultStateRestClient {
    List<AppointmentResultState> findAllStates();

    AppointmentResultState createState(String state);

    Optional<AppointmentResultState> findState(int stateId);

    void updateState(int stateId, String state);

    void deleteState(int stateId);
}
