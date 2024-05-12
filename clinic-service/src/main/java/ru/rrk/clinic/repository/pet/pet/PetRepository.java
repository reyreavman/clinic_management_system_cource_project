package ru.rrk.clinic.repository.pet.pet;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.pet.Pet;

public interface PetRepository extends CrudRepository<Pet, Integer> {
    Iterable<Pet> findAllByNameLikeIgnoreCase(String filter);
}
