package ru.rrk.user.receptionist.mapper;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.dto.pet.Pet;
import ru.rrk.user.receptionist.viewModels.PetViewPrimary;

public class PetViewPrimaryConverter implements Converter<Pet, PetViewPrimary> {
    @Override
    public PetViewPrimary convert(Pet pet) {
        return PetViewPrimary.builder()
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
