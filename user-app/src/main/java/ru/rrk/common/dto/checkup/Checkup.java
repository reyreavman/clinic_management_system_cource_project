package ru.rrk.common.dto.checkup;

import lombok.Builder;
import ru.rrk.common.dto.vet.Vet;
import ru.rrk.common.dto.pet.Pet;

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
        CheckupResult checkupResult
) {
}
