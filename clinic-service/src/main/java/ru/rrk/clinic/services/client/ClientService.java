package ru.rrk.clinic.services.client;

import ru.rrk.clinic.entity.Client;

import java.util.Optional;

public interface ClientService {
    Iterable<Client> findAllClients(String filter);

    Client createClient(String firstName, String lastName, String phoneNumber, String email);

    Optional<Client> findClient(int clientID);

    void updateClient(Integer id, String firstName, String lastName, String phoneNumber, String email);

    void deleteClient(Integer id);
}
