package ru.rrk.clinic.services.client.ClientService;

import ru.rrk.clinic.entity.Client;

import java.util.Optional;

public class DefaultClientService implements ClientService {
    @Override
    public Iterable<Client> findAllClients(String filter) {
        return null;
    }

    @Override
    public Client createClient(String firstName, String lastName, String phoneNumber, String email) {
        return null;
    }

    @Override
    public Optional<Client> findClient(int clientID) {
        return Optional.empty();
    }

    @Override
    public void updateClient(Integer id, String firstName, String lastName, String phoneNumber, String email) {

    }

    @Override
    public void deleteClient(Integer id) {

    }
}
