package ru.rrk.manager.entity.appointments.appointment;


import lombok.Builder;
import ru.rrk.manager.entity.checkup.Checkup;
import ru.rrk.manager.entity.pet.Pet;
import ru.rrk.manager.entity.vet.Vet;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record Appointment(
        int id,
        Pet pet,
        Vet vet,
        LocalDate date,
        LocalTime time,
        String description,
        Checkup checkup
) {
}
