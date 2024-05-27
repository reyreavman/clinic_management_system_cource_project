package ru.rrk.user.receptionist.dto;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record CheckupDTO(
        int id,
        LocalTime time,
        int petId,
        String petName,
        int vetId,
        String vetName,
        String state
) {
}
