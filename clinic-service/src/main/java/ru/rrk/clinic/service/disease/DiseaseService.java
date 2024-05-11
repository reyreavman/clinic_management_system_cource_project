package ru.rrk.clinic.service.disease;

import ru.rrk.clinic.entity.Disease;

import java.util.Optional;

public interface DiseaseService {
    Iterable<Disease> findAllDiseases(String filter);

    Disease createDisease(int code, String description);

    Optional<Disease> findDisease(int diseaseId);

    void updateDisease(Integer id, int code, String description);

    void deleteDisease(Integer id);
}
