package ru.rrk.clinic.repository.checkup.type;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.checkup.CheckupType;

public interface CheckupTypeRepository extends CrudRepository<CheckupType, Integer> {
}
