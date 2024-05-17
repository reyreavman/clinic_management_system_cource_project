package ru.rrk.clinic.repository.diagnosis;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.Diagnosis;

public interface DiagnosisRepository extends CrudRepository<Diagnosis, Integer> {
}
