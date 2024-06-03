package ru.rrk.common.dto.pet;

import java.time.LocalDate;

public record Label(int id, String value, LocalDate date) {
}
