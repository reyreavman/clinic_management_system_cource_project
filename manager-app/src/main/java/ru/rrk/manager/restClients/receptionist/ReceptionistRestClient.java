package ru.rrk.manager.restClients.receptionist;

import ru.rrk.manager.entity.Receptionist;

import java.util.List;
import java.util.Optional;

public interface ReceptionistRestClient {
    List<Receptionist> findAllReceptionists();

    Receptionist createReceptionist(String firstName, String lastName);

    Optional<Receptionist> findReceptionist(int receptionistId);

    void updateReceptionist(int receptionistId, String firstName, String lastName);

    void deleteReceptionist(int receptionistId);
}
