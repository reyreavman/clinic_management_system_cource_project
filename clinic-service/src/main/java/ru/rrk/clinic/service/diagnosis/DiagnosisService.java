package ru.rrk.clinic.service.diagnosis;

import ru.rrk.clinic.entity.Diagnosis;

import java.util.Optional;

public interface DiagnosisService {
    Iterable<Diagnosis> findAllDiagnosis();

    Diagnosis createDiagnosis(Integer diseaseId, String description);

    Optional<Diagnosis> findDiagnosis(Integer diagnosisId);

    void updateDiagnosis(Integer diagnosisId, Integer diseaseId, String description);

    void deleteDiagnosis(Integer diagnosisId);
}
