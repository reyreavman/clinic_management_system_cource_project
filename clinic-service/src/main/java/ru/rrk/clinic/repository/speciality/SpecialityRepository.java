package ru.rrk.clinic.repository.speciality;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.Speciality;

public interface SpecialityRepository extends CrudRepository<Speciality, Integer> {
    Iterable<Speciality> findAllByName(String filter);
}
