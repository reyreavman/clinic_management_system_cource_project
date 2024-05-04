package ru.rrk.manager.restClients.vet;

import ru.rrk.manager.entity.Speciality;
import ru.rrk.manager.entity.Vet;

import java.util.List;
import java.util.Optional;

public interface VetRestClient {
    List<Vet> findAllVets(String filter);

    Vet createVet(String firstName, String lastName, Speciality speciality);

    Optional<Vet> findVet(int vetId);

    void updateVet(int vetId, String firstName, String lastName, Speciality speciality);

    void deleteVet(int vetId);
}
