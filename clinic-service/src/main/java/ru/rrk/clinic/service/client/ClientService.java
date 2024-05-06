package ru.rrk.clinic.service.client;

import ru.rrk.clinic.entity.Client;

import java.util.Optional;

public interface ClientService {
    Iterable<Client> findAllClients(String filter);

    Client createClient(String firstName, String lastName, String phoneNumber, String email);

    Optional<Client> findClient(int clientId);

    void updateClient(Integer id, String firstName, String lastName, String phoneNumber, String email);

    void deleteClient(Integer id);
}