package ru.rrk.user.receptionist.dto;

import ru.rrk.user.receptionist.dto.checkup.Checkup;
import ru.rrk.user.receptionist.dto.pet.Pet;
import ru.rrk.user.receptionist.dto.vet.Vet;

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
