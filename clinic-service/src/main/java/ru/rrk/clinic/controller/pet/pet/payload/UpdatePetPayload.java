package ru.rrk.clinic.controller.pet.pet.payload;

import java.time.LocalDate;

public record UpdatePetPayload(
        String name,
        Integer clientId,
        Integer typeId,
        Integer breedId,
        Integer genderId,
        LocalDate birthday,
        Integer labelId
) {
}
