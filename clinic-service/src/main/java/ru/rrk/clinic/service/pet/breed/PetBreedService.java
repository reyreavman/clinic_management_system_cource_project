package ru.rrk.clinic.service.pet.breed;

import ru.rrk.clinic.entity.pet.PetBreed;

import java.util.Optional;

public interface PetBreedService {
    Iterable<PetBreed> findAllBreeds(String filter);

    PetBreed createBreed(String name, Integer petTypeId);

    Optional<PetBreed> findBreed(int breedId);

    void updateBreed(Integer id, String name, Integer peyTypeId);

    void deleteBreed(Integer id);
}
