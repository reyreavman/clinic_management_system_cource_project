package ru.rrk.clinic.repository.disease;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.Disease;

public interface DiseaseRepository extends CrudRepository<Disease, Integer> {
}
