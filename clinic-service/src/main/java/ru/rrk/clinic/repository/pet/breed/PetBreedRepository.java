package ru.rrk.clinic.repository.pet.breed;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.pet.PetBreed;

public interface PetBreedRepository extends CrudRepository<PetBreed, Integer> {
    Iterable<PetBreed> findAllByNameLikeIgnoreCase(String filter);
}
