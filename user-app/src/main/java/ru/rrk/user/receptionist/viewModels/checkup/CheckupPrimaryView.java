package ru.rrk.user.receptionist.viewModels.checkup;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record CheckupPrimaryView(
        int id,
        LocalDate date,
        LocalTime time,
        int petId,
        String petName,
        int vetId,
        String vetFullName,
        String type,
        String state,
        String result
) {
}
