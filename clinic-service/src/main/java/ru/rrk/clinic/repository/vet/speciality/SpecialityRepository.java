package ru.rrk.clinic.repository.vet.speciality;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.vet.Speciality;

public interface SpecialityRepository extends CrudRepository<Speciality, Integer> {
    Iterable<Speciality> findAllByName(String filter);
}
