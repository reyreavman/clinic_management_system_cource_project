package ru.rrk.clinic.service.vet;

import ru.rrk.clinic.entity.Speciality;
import ru.rrk.clinic.entity.Vet;

import java.util.Optional;
import java.util.Set;

public interface VetService {
    Iterable<Vet> findAllVets(String filter);

    Vet createVet(String firstName, String lastName, Set<Speciality> speciality);

    Optional<Vet> findVet(int vetId);

    void updateVet(Integer id, String firstName, String lastName, Set<Speciality> speciality);

    void deleteVet(Integer id);
}
