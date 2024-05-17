package ru.rrk.manager.restClients.diagnosis;

import ru.rrk.manager.entity.Diagnosis;

import java.util.List;
import java.util.Optional;

public interface DiagnosisRestClient {
    List<Diagnosis> findAllDiagnosis();

    Diagnosis createDiagnosis(Integer diseaseCode, String description);

    Optional<Diagnosis> findDiagnosis(Integer diagnosisId);

    void updateDiagnosis(Integer diagnosisId, Integer diseaseId, String description);

    void deleteDiagnosis(Integer diagnosisId);
}
