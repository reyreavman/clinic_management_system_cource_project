package ru.rrk.user.receptionist.entity;

import ru.rrk.user.receptionist.entity.checkup.Checkup;
import ru.rrk.user.receptionist.entity.pet.Pet;
import ru.rrk.user.receptionist.entity.vet.Vet;

import java.time.LocalDate;
import java.time.LocalTime;

public record Appointment(
        int id,
        Pet pet,
        Vet vet,
        LocalDate date,
        LocalTime time,
        String description,
        Checkup checkup,
        Receptionist receptionist
) {
}
