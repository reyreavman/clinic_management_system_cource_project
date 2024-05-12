package ru.rrk.clinic.repository.pet.type;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.pet.PetType;

public interface PetTypeRepository extends CrudRepository<PetType, Integer> {
    Iterable<PetType> findAllByName(String filter);
}
