package ru.rrk.clinic.service.checkup.state;

import ru.rrk.clinic.entity.checkup.CheckupState;

import java.util.Optional;

public interface CheckupStateService {
    Iterable<CheckupState> findAllStates();

    CheckupState createState(String state);

    Optional<CheckupState> findState(int stateId);

    void updateState(Integer id, String type);

    void deleteState(Integer id);
}
