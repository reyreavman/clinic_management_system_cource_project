package ru.rrk.manager.controller.pets.pet.payload;

import java.time.LocalDate;

public record NewPetPayload(
        String name,
        Integer clientId,
        Integer typeId,
        Integer breedId,
        Integer genderId,
        LocalDate birthday,
        Integer labelId
) {
}
