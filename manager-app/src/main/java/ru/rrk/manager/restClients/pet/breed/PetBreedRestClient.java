package ru.rrk.manager.restClients.pet.breed;

import ru.rrk.manager.entity.pet.PetBreed;

import java.util.List;
import java.util.Optional;

public interface PetBreedRestClient {
    List<PetBreed> findAllBreeds(String filter);

    PetBreed createBreed(String name, Integer typeId);

    Optional<PetBreed> findBreed(int breedId);

    void updateBreed(int breedId, String name, Integer typeId);

    void deleteBreed(int breedId);
}
