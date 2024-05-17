package ru.rrk.manager.restClients.vet.vet;

import ru.rrk.manager.entity.vet.Vet;

import java.util.List;
import java.util.Optional;

public interface VetRestClient {
    List<Vet> findAllVets(String filter);

    Vet createVet(String firstName, String lastName, Integer speciality_id);

    Optional<Vet> findVet(Integer vetId);

    void updateVet(Integer vetId, String firstName, String lastName, Integer speciality_id);

    void deleteVet(Integer vetId);
}
