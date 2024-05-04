package ru.rrk.clinic.services.speciality;

import ru.rrk.clinic.entity.Speciality;

import java.util.Optional;

public interface SpecialityService {
    Iterable<Speciality> findAllSpecialities(String filter);

    Speciality createSpeciality(String name);

    Optional<Speciality> findSpeciality(int specialityId);

    void updateSpeciality(Integer id, String name);

    void deleteSpeciality(Integer id);
}