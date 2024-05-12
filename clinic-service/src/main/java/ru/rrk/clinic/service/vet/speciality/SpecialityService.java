package ru.rrk.clinic.service.vet.speciality;

import ru.rrk.clinic.entity.vet.Speciality;

import java.util.Optional;

public interface SpecialityService {
    Iterable<Speciality> findAllSpecialities(String filter);

    Speciality createSpeciality(String name);

    Optional<Speciality> findSpeciality(int specialityId);

    void updateSpeciality(Integer id, String name);

    void deleteSpeciality(Integer id);
}