package ru.rrk.common.dto.pet;

import ru.rrk.common.dto.Client;

import java.time.LocalDate;

public record Pet(
        int id,
        String name,
        Client client,
        Type type,
        Breed breed,
        Gender gender,
        LocalDate birthday,
        Label label
) {
}
