package ru.rrk.user.receptionist.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class NewCheckupPayload {
    private final LocalDate date;
    private final LocalTime time;
    private final Integer petId;
    private final Integer vetId;
    private final Integer typeId;
    @Setter
    private Integer stateId;
    @Setter
    private Integer resultId;
}
