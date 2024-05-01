package ru.rrk.clinic.services.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.Client;
import ru.rrk.clinic.repository.client.ClientRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultClientService implements ClientService {
    private final ClientRepository repository;

    @Override
    public Iterable<Client> findAllClients(String filter) {
        if (filter != null && !filter.isBlank())
            return this.repository.findAllByFirstNameIgnoreCase("%" + filter + "%");
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public Client createClient(String firstName, String lastName, String phoneNumber, String email) {
        return this.repository.save(new Client(null, firstName, lastName, phoneNumber, email));
    }

    @Override
    public Optional<Client> findClient(int clientID) {
        return this.repository.findById(clientID);
    }

    @Override
    @Transactional
    public void updateClient(Integer id, String firstName, String lastName, String phoneNumber, String email) {
        this.repository.findById(id)
                .ifPresentOrElse(client -> {
                    client.setFirstName(firstName);
                    client.setLastName(lastName);
                    client.setPhoneNumber(phoneNumber);
                    client.setEmail(email);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteClient(Integer id) {
        this.repository.deleteById(id);
    }
}
