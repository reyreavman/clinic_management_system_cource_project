package ru.rrk.manager.restClients.checkup.state;

import ru.rrk.manager.entity.checkup.CheckupState;

import java.util.List;
import java.util.Optional;

public interface CheckupStateRestClient {
    List<CheckupState> findAllStates();

    CheckupState createState(String state);

    Optional<CheckupState> findState(int stateId);

    void updateState(int stateId, String state);

    void deleteState(int stateId);
}
