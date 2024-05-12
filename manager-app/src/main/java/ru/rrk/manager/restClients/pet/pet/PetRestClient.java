package ru.rrk.manager.restClients.pet.pet;

import ru.rrk.manager.entity.pet.Pet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PetRestClient {
    List<Pet> findAllPets(String filter);

    Pet createPet(String name, Integer clientId, Integer typeId, Integer breedId, Integer genderId, LocalDate birthday, Integer labelId);

    Optional<Pet> findPet(int petId);

    void updatePet(Integer petId, String name, Integer clientId, Integer typeId, Integer breedId, Integer genderId, LocalDate birthday, Integer labelId);

    void deletePet(Integer petId);
}
