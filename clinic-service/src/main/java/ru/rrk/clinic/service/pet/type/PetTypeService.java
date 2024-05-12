package ru.rrk.clinic.service.pet.type;

import ru.rrk.clinic.entity.pet.PetType;

import java.util.Optional;

public interface PetTypeService {
    Iterable<PetType> findAllTypes(String filter);

    PetType createType(String name);

    Optional<PetType> findType(int typeId);

    void updateType(Integer typeId, String name);

    void deleteType(Integer typeId);
}
