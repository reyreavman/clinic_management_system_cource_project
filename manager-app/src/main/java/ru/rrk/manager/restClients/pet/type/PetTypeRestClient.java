package ru.rrk.manager.restClients.pet.type;

import ru.rrk.manager.entity.pet.PetType;

import java.util.List;
import java.util.Optional;

public interface PetTypeRestClient {
    List<PetType> findAllTypes(String filter);

    PetType createType(String name);

    Optional<PetType> findType(int typeId);

    void updateType(int typeId, String name);

    void deleteType(int typeId);
}
