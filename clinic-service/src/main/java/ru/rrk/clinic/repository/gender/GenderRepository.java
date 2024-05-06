package ru.rrk.clinic.repository.gender;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.Gender;

public interface GenderRepository extends CrudRepository<Gender, Integer> {
}
