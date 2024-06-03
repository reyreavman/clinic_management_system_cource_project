package ru.rrk.common.mapper.pet;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.common.dto.pet.Pet;
import ru.rrk.common.viewModels.pet.PetPrimaryView;

public class PetViewPrimaryConverter implements Converter<Pet, PetPrimaryView> {
    @Override
    public PetPrimaryView convert(Pet pet) {
        return PetPrimaryView.builder()
                .id(pet.id())
                .name(pet.name())
                .clientId(pet.client().id())
                .clientFullName(pet.client().firstName().concat(" ").concat(pet.client().lastName()))
                .type(pet.type().name())
                .breed(pet.breed().name())
                .birthday(pet.birthday())
                .labelDate(pet.label().date())
                .labelValue(pet.label().value())
                .build();
    }
}
