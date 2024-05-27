package ru.rrk.user.receptionist.entity.checkup;

import lombok.Builder;
import ru.rrk.user.receptionist.entity.pet.Pet;
import ru.rrk.user.receptionist.entity.vet.Vet;

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
