package ru.rrk.clinic.service.diagnosis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.Diagnosis;
import ru.rrk.clinic.repository.diagnosis.DiagnosisRepository;
import ru.rrk.clinic.repository.disease.DiseaseRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultDiagnosisService implements DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;
    private final DiseaseRepository diseaseRepository;

    @Override
    public Iterable<Diagnosis> findAllDiagnosis() {
        return this.diagnosisRepository.findAll();
    }

    @Override
    @Transactional
    public Diagnosis createDiagnosis(Integer diseaseId, String description) {
        return this.diagnosisRepository.save(new Diagnosis(
                null,
                this.diseaseRepository.findById(diseaseId).orElseThrow(NoSuchElementException::new),
                description
        ));
    }

    @Override
    public Optional<Diagnosis> findDiagnosis(Integer diagnosisId) {
        return this.diagnosisRepository.findById(diagnosisId);
    }

    @Override
    @Transactional
    public void updateDiagnosis(Integer diagnosisId, Integer diseaseId, String description) {
        this.diagnosisRepository.findById(diagnosisId)
                .ifPresentOrElse(diagnosis -> {
                    diagnosis.setDisease(this.diseaseRepository.findById(diseaseId).orElseThrow(NoSuchElementException::new));
                    diagnosis.setDescription(description);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteDiagnosis(Integer diagnosisId) {
        this.diagnosisRepository.deleteById(diagnosisId);
    }
}
