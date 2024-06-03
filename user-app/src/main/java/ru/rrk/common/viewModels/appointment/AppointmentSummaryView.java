package ru.rrk.common.viewModels.appointment;

import lombok.Builder;
import ru.rrk.common.dto.checkup.Checkup;

import java.time.LocalTime;

@Builder
public record AppointmentSummaryView(
        int id,
        int petId,
        String petName,
        int vetId,
        String vetFullName,
        LocalTime time,
        String description,
        Checkup checkup
) {
}
