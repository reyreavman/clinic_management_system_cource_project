package ru.rrk.manager.entity.checkup;

import lombok.Builder;
import ru.rrk.manager.entity.pet.Pet;
import ru.rrk.manager.entity.vet.Vet;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record Checkup(
        int id,
        LocalDate date,
        LocalTime time,
        Pet pet,
        Vet vet,
        CheckupType checkupType,
        CheckupState checkupState,
        CheckupResult checkupResult) {
}
