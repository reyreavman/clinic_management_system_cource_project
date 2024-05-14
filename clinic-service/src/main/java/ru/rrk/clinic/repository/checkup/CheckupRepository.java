package ru.rrk.clinic.repository.checkup;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.checkup.Checkup;

public interface CheckupRepository extends CrudRepository<Checkup, Integer> {
}
