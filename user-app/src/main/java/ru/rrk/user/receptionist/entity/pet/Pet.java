package ru.rrk.user.receptionist.entity.pet;

import ru.rrk.user.receptionist.entity.Client;

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
