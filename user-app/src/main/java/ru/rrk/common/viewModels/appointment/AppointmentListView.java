package ru.rrk.common.viewModels.appointment;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AppointmentListView(
        int id,
        int petId,
        String petName,
        int vetId,
        String vetFullName,
        LocalDate date,
        LocalTime time
) {
}