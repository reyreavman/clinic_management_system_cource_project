package ru.rrk.clinic.service.receptionist;

import ru.rrk.clinic.entity.Receptionist;

import java.util.Optional;

public interface ReceptionistService {
    Iterable<Receptionist> findAllReceptionists(String filter);

    Receptionist createReceptionist(String firstName, String lastName);

    Optional<Receptionist> findReceptionist(int receptionistId);

    void updateReceptionist(Integer id, String firstName, String lastName);

    void deleteReceptionist(Integer id);
}
