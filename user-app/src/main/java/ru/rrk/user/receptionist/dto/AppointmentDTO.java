package ru.rrk.user.receptionist.dto;

import lombok.Builder;
import ru.rrk.user.receptionist.entity.checkup.Checkup;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AppointmentDTO(
        int id,
        int petId,
        String petName,
        int vetId,
        String vetFullName,
        LocalDate date,
        LocalTime time,
        String description,
        Checkup checkup
) {
}
