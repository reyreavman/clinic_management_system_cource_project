package ru.rrk.user.receptionist.viewModels;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetViewPrimary(
        int id,
        String name,
        int clientId,
        String clientFullName,
        String type,
        String breed,
        LocalDate birthday,
        LocalDate labelDate,
        String labelValue) {
}
