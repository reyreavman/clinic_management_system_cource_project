package ru.rrk.clinic.repository.vet;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.vet.Vet;

public interface VetRepository extends CrudRepository<Vet, Integer> {
    Iterable<Vet> findAllByFirstNameLikeIgnoreCase(String filter);
}
