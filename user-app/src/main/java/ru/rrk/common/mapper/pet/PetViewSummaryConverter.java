package ru.rrk.common.mapper.pet;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.common.dto.pet.Pet;
import ru.rrk.common.viewModels.pet.PetSummaryView;

public class PetViewSummaryConverter implements Converter<Pet, PetSummaryView> {
    @Override
    public PetSummaryView convert(Pet pet) {
        return new PetSummaryView(pet.id(), pet.name());
    }
}
