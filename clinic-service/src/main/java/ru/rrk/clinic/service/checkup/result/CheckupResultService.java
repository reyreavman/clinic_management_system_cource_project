package ru.rrk.clinic.service.checkup.result;

import ru.rrk.clinic.entity.checkup.CheckupResult;

import java.util.Optional;

public interface CheckupResultService {
    Iterable<CheckupResult> findAllResults();

    CheckupResult createResult(String description);

    Optional<CheckupResult> findResult(int resultId);

    void updateResult(int resultId, String description);

    void deleteResult(int resultId);
}
