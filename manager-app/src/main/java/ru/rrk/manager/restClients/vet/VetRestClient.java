package ru.rrk.manager.restClients.vet;

import ru.rrk.manager.entity.Vet;

import java.util.List;
import java.util.Optional;

public interface VetRestClient {
    List<Vet> findAllVets(String filter);

    Vet createVet(String firstName, String lastName, Integer speciality_id);

    Optional<Vet> findVet(int vetId);

    void updateVet(int vetId, String firstName, String lastName, Integer speciality_id);

    void deleteVet(int vetId);
}
