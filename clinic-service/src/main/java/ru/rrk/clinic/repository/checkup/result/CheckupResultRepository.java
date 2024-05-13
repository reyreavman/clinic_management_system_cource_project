package ru.rrk.clinic.repository.checkup.result;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.checkup.CheckupResult;

public interface CheckupResultRepository extends CrudRepository<CheckupResult, Integer> {
}
