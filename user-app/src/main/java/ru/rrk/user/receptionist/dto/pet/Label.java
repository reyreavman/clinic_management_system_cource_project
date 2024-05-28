package ru.rrk.user.receptionist.dto.pet;

import java.time.LocalDate;

public record Label(int id, String value, LocalDate date) {
}
