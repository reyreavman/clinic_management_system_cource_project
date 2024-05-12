package ru.rrk.manager.entity.pet;

import ru.rrk.manager.entity.Client;
import ru.rrk.manager.entity.Gender;

import java.time.LocalDate;

public record Pet(
        int id,
        String name,
        Client client,
        PetType type,
        PetBreed breed,
        Gender gender,
        LocalDate birthday,
        Label label
) {
}
