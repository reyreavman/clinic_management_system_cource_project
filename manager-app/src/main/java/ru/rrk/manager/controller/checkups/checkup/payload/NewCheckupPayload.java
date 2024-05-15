package ru.rrk.manager.controller.checkups.checkup.payload;

import java.time.LocalDate;
import java.time.LocalTime;

public record NewCheckupPayload(
        LocalDate date,
        LocalTime time,
        Integer petId,
        Integer vetId,
        Integer checkupTypeId,
        Integer checkupStateId,
        Integer checkupResultId) {
}
