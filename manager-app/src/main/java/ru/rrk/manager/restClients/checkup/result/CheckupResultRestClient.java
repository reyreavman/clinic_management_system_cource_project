package ru.rrk.manager.restClients.checkup.result;

import ru.rrk.manager.entity.checkup.CheckupResult;

import java.util.List;
import java.util.Optional;

public interface CheckupResultRestClient {
    List<CheckupResult> findAllResults();

    CheckupResult createResult(String description);

    Optional<CheckupResult> findResult(int resultId);

    void updateResult(int resultId, String description);

    void deleteResult(int resultId);
}
