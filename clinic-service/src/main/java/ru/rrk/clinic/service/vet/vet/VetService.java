package ru.rrk.clinic.service.vet.vet;

import ru.rrk.clinic.entity.vet.Vet;

import java.util.Optional;

public interface VetService {
    Iterable<Vet> findAllVets(String filter);

    Vet createVet(String firstName, String lastName, Integer speciality_id);

    Optional<Vet> findVet(int vetId);

    void updateVet(Integer id, String firstName, String lastName, Integer speciality_id);

    void deleteVet(Integer id);
}
