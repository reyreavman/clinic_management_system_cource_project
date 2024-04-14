package ru.rrk.clinic.repository.client;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    Iterable<Client> findAllByFirstNameIgnoreCase(String filter);
}
