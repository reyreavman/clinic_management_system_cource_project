package ru.rrk.manager.restClients.speciality;

import ru.rrk.manager.entity.Speciality;

import java.util.List;
import java.util.Optional;

public interface SpecialityRestClient {
    List<Speciality> findAllSpecialities(String filter);

    Speciality createSpeciality(String name);

    Optional<Speciality> findSpeciality(int specialityId);

    void updateSpeciality(int specialityId, String name);

    void deleteSpeciality(int specialityId);
}
