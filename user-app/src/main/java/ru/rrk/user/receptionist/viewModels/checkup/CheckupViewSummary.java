package ru.rrk.user.receptionist.viewModels.checkup;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record CheckupViewSummary(
        int id,
        LocalTime time,
        int petId,
        String petName,
        int vetId,
        String vetName,
        String state
) {
}
