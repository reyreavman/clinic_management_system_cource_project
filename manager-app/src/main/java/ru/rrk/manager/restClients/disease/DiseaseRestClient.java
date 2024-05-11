package ru.rrk.manager.restClients.disease;

import ru.rrk.manager.entity.Disease;

import java.util.List;
import java.util.Optional;

public interface DiseaseRestClient {
    List<Disease> findAllDiseases(String filter);

    Disease createDisease(int code, int description);

    Optional<Disease> findDisease(int diseaseId);

    void updateDisease(int diseaseId, int code, String description);

    void deleteDisease(int diseaseId);
}
