package ru.rrk.user.receptionist.viewModels.appointment;

import lombok.Builder;
import ru.rrk.user.receptionist.dto.checkup.Checkup;

import java.time.LocalTime;

@Builder
public record AppointmentViewSummary(
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
