package ru.rrk.clinic.service.pet.pet;

import ru.rrk.clinic.entity.pet.Pet;

import java.time.LocalDate;
import java.util.Optional;

public interface PetService {
    Iterable<Pet> findAllPets(String filter);

    Pet createPet(String name, Integer clientId, Integer typeId, Integer breedId, Integer genderId, LocalDate birthday, Integer labelId);

    Optional<Pet> findPet(Integer petId);

    void updatePet(Integer petId, String name, Integer clientId, Integer typeId, Integer breedId, Integer genderId, LocalDate birthday, Integer labelId);

    void deletePet(Integer petId);
}
