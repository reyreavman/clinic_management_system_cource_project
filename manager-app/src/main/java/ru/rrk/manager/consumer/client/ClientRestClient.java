package ru.rrk.manager.consumer.client;

import ru.rrk.manager.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRestClient {
    List<Client> findAllClients(String filter);

    Client createClient(String firstName, String lastName, String phoneNumber, String email);

    Optional<Client> findClient(int clientId);

    void updateClient(int clientId, String firstName, String lastName, String phoneNumber, String email);

    void deleteClient(int clientId);
}
