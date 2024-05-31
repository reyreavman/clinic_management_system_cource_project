package ru.rrk.user.receptionist.mapper.pet;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.dto.pet.Pet;
import ru.rrk.user.receptionist.viewModels.pet.PetSummaryView;

public class PetViewSummaryConverter implements Converter<Pet, PetSummaryView> {
    @Override
    public PetSummaryView convert(Pet pet) {
        return new PetSummaryView(pet.id(), pet.name());
    }
}
