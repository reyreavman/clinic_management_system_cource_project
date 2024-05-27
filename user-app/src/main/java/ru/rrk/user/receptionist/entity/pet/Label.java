package ru.rrk.user.receptionist.entity.pet;

import java.time.LocalDate;

public record Label(int id, String value, LocalDate date) {
}
