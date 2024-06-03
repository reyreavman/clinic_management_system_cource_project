package ru.rrk.common.dto;

import ru.rrk.common.dto.checkup.Checkup;
import ru.rrk.common.dto.pet.Pet;
import ru.rrk.common.dto.vet.Vet;

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
