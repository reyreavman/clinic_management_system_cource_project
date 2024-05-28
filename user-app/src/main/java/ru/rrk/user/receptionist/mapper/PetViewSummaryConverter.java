package ru.rrk.user.receptionist.mapper;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.dto.pet.Pet;
import ru.rrk.user.receptionist.viewModels.PetViewSummary;

public class PetViewSummaryConverter implements Converter<Pet, PetViewSummary> {
    @Override
    public PetViewSummary convert(Pet pet) {
        return new PetViewSummary(pet.id(), pet.name());
    }
}
