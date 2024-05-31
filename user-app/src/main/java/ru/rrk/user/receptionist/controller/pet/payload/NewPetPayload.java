package ru.rrk.user.receptionist.controller.pet.payload;

import java.time.LocalDate;

public record NewPetPayload(
        String name,
        Integer clientId,
        Integer typeId,
        Integer breedId,
        Integer genderId,
        Integer labelId,
        LocalDate birthday
) {
}
