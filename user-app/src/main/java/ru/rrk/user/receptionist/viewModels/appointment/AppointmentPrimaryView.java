package ru.rrk.user.receptionist.viewModels.appointment;

import lombok.Builder;
import ru.rrk.user.receptionist.dto.checkup.Checkup;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AppointmentPrimaryView(
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
