package ru.rrk.user.receptionist.dto.checkup;

import lombok.Builder;
import ru.rrk.user.receptionist.dto.pet.Pet;
import ru.rrk.user.receptionist.dto.vet.Vet;

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
